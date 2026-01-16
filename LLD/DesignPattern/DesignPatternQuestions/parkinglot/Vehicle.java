package DesignPattern.DesignPatternQuestions.parkinglot;

public abstract class Vehicle {
    protected String licensePlate;
    protected VehicleType vehicleType;
    protected String color;
    protected String model;

    public Vehicle(String licensePlate, VehicleType vehicleType, String color, String model) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.color = color;
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getColor() {
        return color;
    }

    public String getModel() {
        return model;
    }

    @Override
    public String toString() {
        return "Vehicle {" +
                "licensePlate='" + licensePlate + '\'' +
                ", vehicleType=" + vehicleType +
                ", color='" + color + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
