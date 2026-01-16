package DesignPattern.DesignPatternQuestions.parkinglot;

public class ParkingSpot {
    private int spotNumber;
    private int levelNumber;
    private ParkingSpotStatus status;
    private int size; // Size in terms of vehicle units (1 for motorcycle, 2 for car, 3 for truck)
    private Vehicle parkedVehicle;

    public ParkingSpot(int spotNumber, int levelNumber, int size) {
        this.spotNumber = spotNumber;
        this.levelNumber = levelNumber;
        this.size = size;
        this.status = ParkingSpotStatus.AVAILABLE;
    }

    public synchronized boolean parkVehicle(Vehicle vehicle) {
        if (status != ParkingSpotStatus.AVAILABLE) {
            return false;
        }
        if (vehicle.getVehicleType().getSpotSize() > size) {
            return false;
        }
        this.parkedVehicle = vehicle;
        this.status = ParkingSpotStatus.OCCUPIED;
        return true;
    }

    public synchronized boolean unparkVehicle() {
        if (status != ParkingSpotStatus.OCCUPIED || parkedVehicle == null) {
            return false;
        }
        this.parkedVehicle = null;
        this.status = ParkingSpotStatus.AVAILABLE;
        return true;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public ParkingSpotStatus getStatus() {
        return status;
    }

    public int getSize() {
        return size;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public boolean isAvailable() {
        return status == ParkingSpotStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "spotNumber=" + spotNumber +
                ", levelNumber=" + levelNumber +
                ", status=" + status +
                ", size=" + size +
                '}';
    }
}
