package DesignPattern.DesignPatternQuestions.parkinglot;

public enum VehicleType {
    MOTORCYCLE(1),
    CAR(2),
    TRUCK(3);

    private int spotSize;

    VehicleType(int spotSize) {
        this.spotSize = spotSize;
    }

    public int getSpotSize() {
        return spotSize;
    }
}
