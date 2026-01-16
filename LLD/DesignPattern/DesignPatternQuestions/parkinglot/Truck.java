package DesignPattern.DesignPatternQuestions.parkinglot;

public class Truck extends Vehicle {
    public Truck(String licensePlate, String color, String model) {
        super(licensePlate, VehicleType.TRUCK, color, model);
    }
}
