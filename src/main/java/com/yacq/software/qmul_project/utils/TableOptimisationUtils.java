package com.yacq.software.qmul_project.utils;

import com.yacq.software.qmul_project.model.table.TableSection;

import java.util.*;

public class TableOptimisationUtils {

    public static final Set<String> WINDOW_SYNONYMS = Set.of(
            "window", "windows", "view", "views", "vista", "vistas", "panorama",
            "outside", "natural light", "daylight", "bright", "well lit",
            "sunny", "scenic", "garden view", "street view", "city view",
            "ocean view", "water view", "landscape", "by the glass",
            "sunlight", "daylit", "illuminated", "airy", "well illuminated"
    );

    public static final Set<String> CENTRE_SYNONYMS = Set.of(
            "center", "centre", "middle", "central", "heart", "main",
            "hub", "core", "action", "lively", "bustling", "energetic",
            "vibrant", "where it's happening", "in the thick of things",
            "social", "communal", "group", "party", "festive"
    );

    public static final Set<String> BAR_SYNONYMS = Set.of(
            "bar", "counter", "pub", "tavern", "lounge", "drinks",
            "cocktails", "bartender", "mixologist", "stools",
            "high tops", "high table", "happy hour", "by the drink",
            "near the alcohol", "where the drinks are", "counter seating",
            "mixology", "aperitif", "digestif", "bartop"
    );

    public static final Set<String> PRIVATE_SYNONYMS = Set.of(
            "private", "quiet", "secluded", "hidden", "intimate",
            "romantic", "discreet", "alone", "away from others",
            "cocooned", "isolated", "solitary", "peaceful", "tranquil",
            "calm", "serene", "low-key", "undisturbed", "candlelit",
            "cozy", "cosy", "snug", "booth", "corner", "nook",
            "for two", "date night", "anniversary", "proposal"
    );

    public static final Set<String> EVENT_SYNONYMS = Set.of(
            "event", "party", "celebration", "gathering", "function",
            "group", "large table", "big table", "banquet", "reception",
            "get together", "reunion", "birthday", "anniversary",
            "corporate", "business", "meeting", "conference",
            "presentation", "networking", "team dinner", "family style",
            "communal", "shared", "long table", "round table",
            "reserved area", "section", "private room", "semiprivate"
    );

    public static final Set<String> NEGATIVE_TERMS = Set.of(
            "not", "don't", "avoid", "away from", "no", "without",
            "rather not", "prefer not", "not too", "less", "isn't",
            "wasn't", "weren't", "doesn't", "didn't", "never"
    );

    public static final Map<TableSection, Set<String>> SECTION_SYNONYMS = Map.of(
            TableSection.WINDOW, WINDOW_SYNONYMS,
            TableSection.CENTRE, CENTRE_SYNONYMS,
            TableSection.BAR, BAR_SYNONYMS,
            TableSection.PRIVATE, PRIVATE_SYNONYMS,
            TableSection.EVENT, EVENT_SYNONYMS
    );
}
