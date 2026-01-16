package DesignPattern.DesignPatternQuestions.parkinglot;

public class Car extends Vehicle {
    public Car(String licensePlate, String color, String model) {
        super(licensePlate, VehicleType.CAR, color, model);
    }
}
