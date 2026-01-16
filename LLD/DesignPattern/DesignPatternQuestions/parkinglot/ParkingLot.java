package DesignPattern.DesignPatternQuestions.parkinglot;

import java.util.*;

public class ParkingLot {
    private static ParkingLot instance; // Singleton
    private List<Level> levels;
    private Map<String, ParkingTicket> tickets; // Map of license plate to ticket
    private Map<VehicleType, Rate> rates;

    private ParkingLot() {
        this.levels = new ArrayList<>();
        this.tickets = new HashMap<>();
        this.rates = new HashMap<>();
        initializeRates();
    }

    // Singleton pattern - thread-safe
    public static synchronized ParkingLot getInstance() {
        if (instance == null) {
            instance = new ParkingLot();
        }
        return instance;
    }

    private void initializeRates() {
        rates.put(VehicleType.MOTORCYCLE, new Rate(VehicleType.MOTORCYCLE, 10.0));
        rates.put(VehicleType.CAR, new Rate(VehicleType.CAR, 20.0));
        rates.put(VehicleType.TRUCK, new Rate(VehicleType.TRUCK, 30.0));
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public ParkingTicket parkVehicle(Vehicle vehicle) {
        // Find available spot in any level
        ParkingSpot spot = null;
        for (Level level : levels) {
            spot = level.findAvailableSpot(vehicle);
            if (spot != null) {
                break;
            }
        }

        if (spot == null) {
            System.out.println("No available spot for vehicle: " + vehicle.getLicensePlate());
            return null;
        }

        // Park the vehicle
        if (spot.parkVehicle(vehicle)) {
            String ticketId = generateTicketId();
            ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, spot);
            tickets.put(vehicle.getLicensePlate(), ticket);
            System.out.println("Vehicle parked successfully!");
            System.out.println("Ticket: " + ticket);
            return ticket;
        }

        return null;
    }

    public boolean unparkVehicle(String licensePlate) {
        ParkingTicket ticket = tickets.get(licensePlate);
        if (ticket == null) {
            System.out.println("Ticket not found for license plate: " + licensePlate);
            return false;
        }

        ParkingSpot spot = ticket.getParkingSpot();
        if (spot.unparkVehicle()) {
            // Process exit and calculate parking fee
            Rate rate = rates.get(ticket.getVehicle().getVehicleType());
            ticket.processExit(rate);
            System.out.println("Vehicle unparked successfully!");
            System.out.println("Parking Fee: $" + String.format("%.2f", ticket.getAmount()));
            return true;
        }

        return false;
    }

    public boolean processPayment(String licensePlate) {
        ParkingTicket ticket = tickets.get(licensePlate);
        if (ticket == null) {
            System.out.println("Ticket not found for license plate: " + licensePlate);
            return false;
        }

        if (ticket.isPaid()) {
            System.out.println("Payment already processed for this ticket.");
            return true;
        }

        ticket.markAsPaid();
        System.out.println("Payment processed successfully! Amount: $" + String.format("%.2f", ticket.getAmount()));
        return true;
    }

    public ParkingTicket getTicket(String licensePlate) {
        return tickets.get(licensePlate);
    }

    public void displayParkingLot() {
        System.out.println("\n========== PARKING LOT STATUS ==========");
        for (Level level : levels) {
            level.displayLevel();
        }
        System.out.println("========================================\n");
    }

    public int getTotalAvailableSpots() {
        int count = 0;
        for (Level level : levels) {
            count += level.getAvailableSpotCount();
        }
        return count;
    }

    public int getTotalSpots() {
        int count = 0;
        for (Level level : levels) {
            count += level.getTotalSpots();
        }
        return count;
    }

    private String generateTicketId() {
        return "TICKET-" + System.currentTimeMillis();
    }

    public void setRate(VehicleType vehicleType, double hourlyRate) {
        if (rates.containsKey(vehicleType)) {
            rates.get(vehicleType).setHourlyRate(hourlyRate);
        }
    }

    public double getRate(VehicleType vehicleType) {
        return rates.get(vehicleType).getHourlyRate();
    }
}
