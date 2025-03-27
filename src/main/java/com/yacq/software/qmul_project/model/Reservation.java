package com.yacq.software.qmul_project.model;

public class Reservation {

    private String reservationId;
    private String customerId;
    private Long reservationDate;
    private int numberOfPeople;
    private Table tableNumber;

    // Getters and Setters


    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Long getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Long reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public Table getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Table tableNumber) {
        this.tableNumber = tableNumber;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", reservationDate=" + reservationDate +
                ", numberOfPeople=" + numberOfPeople +
                ", tableNumber=" + tableNumber +
                '}';
    }
}
