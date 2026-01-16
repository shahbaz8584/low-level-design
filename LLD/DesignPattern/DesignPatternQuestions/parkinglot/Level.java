package DesignPattern.DesignPatternQuestions.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private int levelNumber;
    private List<ParkingSpot> parkingSpots;
    private int motorcycleSpots;
    private int carSpots;
    private int truckSpots;

    public Level(int levelNumber, int motorcycleSpots, int carSpots, int truckSpots) {
        this.levelNumber = levelNumber;
        this.motorcycleSpots = motorcycleSpots;
        this.carSpots = carSpots;
        this.truckSpots = truckSpots;
        this.parkingSpots = new ArrayList<>();
        initializeSpots();
    }

    private void initializeSpots() {
        int spotNumber = 1;

        // Initialize motorcycle spots (size 1)
        for (int i = 0; i < motorcycleSpots; i++) {
            parkingSpots.add(new ParkingSpot(spotNumber++, levelNumber, VehicleType.MOTORCYCLE.getSpotSize()));
        }

        // Initialize car spots (size 2)
        for (int i = 0; i < carSpots; i++) {
            parkingSpots.add(new ParkingSpot(spotNumber++, levelNumber, VehicleType.CAR.getSpotSize()));
        }

        // Initialize truck spots (size 3)
        for (int i = 0; i < truckSpots; i++) {
            parkingSpots.add(new ParkingSpot(spotNumber++, levelNumber, VehicleType.TRUCK.getSpotSize()));
        }
    }

    public ParkingSpot findAvailableSpot(Vehicle vehicle) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.isAvailable() && spot.getSize() >= vehicle.getVehicleType().getSpotSize()) {
                return spot;
            }
        }
        return null;
    }

    public int getAvailableSpotCount() {
        int count = 0;
        for (ParkingSpot spot : parkingSpots) {
            if (spot.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    public int getTotalSpots() {
        return parkingSpots.size();
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public void displayLevel() {
        System.out.println("\n=== Level " + levelNumber + " ===");
        System.out.println("Total Spots: " + getTotalSpots());
        System.out.println("Available Spots: " + getAvailableSpotCount());
        for (ParkingSpot spot : parkingSpots) {
            System.out.println("Spot " + spot.getSpotNumber() + " - " + spot.getStatus());
        }
    }
}
