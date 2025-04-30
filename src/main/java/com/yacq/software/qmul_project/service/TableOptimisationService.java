package com.yacq.software.qmul_project.service;

import com.yacq.software.qmul_project.model.reservation.Reservation;
import com.yacq.software.qmul_project.model.table.Table;
import com.yacq.software.qmul_project.model.table.TableSection;

import java.util.*;

import static com.yacq.software.qmul_project.utils.TableOptimisationUtils.NEGATIVE_TERMS;
import static com.yacq.software.qmul_project.utils.TableOptimisationUtils.SECTION_SYNONYMS;

public class TableOptimisationService {

    public static Optional<TableSection> analysePreference(String request) {
        if (request == null || request.isBlank()) return Optional.empty();

        String normalisedRequest = request.toLowerCase()
                .replaceAll("[^a-z\\s]", ""); // Remove punctuation

        // Check for negative terms first
        boolean isNegative = Arrays.stream(normalisedRequest.split("\\s+"))
                .anyMatch(NEGATIVE_TERMS::contains);

        // 2. Score each section
        Map<TableSection, Integer> scores = new EnumMap<>(TableSection.class);
        List<String> words = Arrays.asList(normalisedRequest.split("\\s+"));

        SECTION_SYNONYMS.forEach((section, synonyms) -> {
            int score = (int) words.stream()
                    .filter(synonyms::contains)
                    .count();
            scores.put(section, score);
        });

        // 3. Get top-scoring section
        Optional<TableSection> preferredSection = scores.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);

        // 4. Apply negation if needed
        return preferredSection.map(section ->
                isNegative ? TableSection.valueOf("NOT_" + section.name()) : section
        );
    }

    // Fast check for specific section
    public static boolean prefersSection(String request, TableSection targetSection) {
        return analysePreference(request)
                .filter(section -> section == targetSection ||
                        section.name().equals("NOT_" + targetSection.name()))
                .isPresent();
    }

    public static double calculateProximityScore(Table table, Reservation request) {
        double score = 0;

        //Get preferred section from request
        Optional<TableSection> preferredSection = analysePreference(request.getSpecialRequests());

        //Section match (60% weight)
        if (preferredSection.isPresent() &&
                table.getSection() == preferredSection.get()) {
            score += 60;
        } else if (preferredSection.isEmpty()) {
            // Default scoring when no preference
            score += 30;
        }

        //Distance to entrance (y=15) for accessibility (20% weight)
        score += (15 - table.getPosition().getY()) * 0.2;

        //Group size matching (20% weight)
        score += Math.max(0, 20 - Math.abs(table.getSize() - request.getPartySize()));

        //View quality bonuses
        if (table.getSection() == TableSection.WINDOW) {
            score += (table.getPosition().getX() == 0) ? 15 : 10;
        }

        // Penalize mismatches with explicit NOT_ requests
        if (preferredSection.isPresent() &&
                preferredSection.get().name().startsWith("NOT_") &&
                table.getSection() == TableSection.valueOf(
                        preferredSection.get().name().substring(4))) {
            score -= 50;
        }

        return score;
    }

    public static List<Table> rankTablesByScore(List<Table> tables, Reservation request) {
        // Map to store tables with their calculated scores
        Map<Table, Double> tableScores = new HashMap<>();

        // Calculate the score for each table and store it in the map
        for (Table table : tables) {
            double score = calculateProximityScore(table, request);
            tableScores.put(table, score);
        }

        // Sort the tables by their scores in descending order
        List<Table> rankedTables = new ArrayList<>(tableScores.keySet());
        rankedTables.sort((t1, t2) -> Double.compare(tableScores.get(t2), tableScores.get(t1)));

        return rankedTables;
    }


}
