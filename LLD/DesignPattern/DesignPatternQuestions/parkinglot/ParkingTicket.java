package DesignPattern.DesignPatternQuestions.parkinglot;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingTicket {
    private String ticketId;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amount;
    private boolean isPaid;

    public ParkingTicket(String ticketId, Vehicle vehicle, ParkingSpot parkingSpot) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryTime = LocalDateTime.now();
        this.isPaid = false;
    }

    public void processExit(Rate rate) {
        this.exitTime = LocalDateTime.now();
        calculateAmount(rate);
    }

    private void calculateAmount(Rate rate) {
        if (entryTime != null && exitTime != null) {
            long minutes = ChronoUnit.MINUTES.between(entryTime, exitTime);
            long hours = (minutes + 59) / 60; // Round up to nearest hour
            this.amount = hours * rate.getHourlyRate();
        }
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void markAsPaid() {
        this.isPaid = true;
    }

    @Override
    public String toString() {
        return "ParkingTicket{" +
                "ticketId='" + ticketId + '\'' +
                ", vehicle=" + vehicle +
                ", parkingSpot=" + parkingSpot +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                ", amount=" + amount +
                ", isPaid=" + isPaid +
                '}';
    }
}
