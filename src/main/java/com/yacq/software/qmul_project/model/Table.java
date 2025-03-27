package com.yacq.software.qmul_project.model;

public class Table {
    private int tableNumber;
    private int numberOfSeats;
    private boolean isBooked;

    // Getters and Setters
    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }


    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableNumber=" + tableNumber +
                ", numberOfSeats=" + numberOfSeats +
                ", isBooked=" + isBooked +
                '}';
    }
}