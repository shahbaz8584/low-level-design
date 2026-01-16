package DesignPattern.DesignPatternQuestions.parkinglot;

public class Rate {
    private VehicleType vehicleType;
    private double hourlyRate;

    public Rate(VehicleType vehicleType, double hourlyRate) {
        this.vehicleType = vehicleType;
        this.hourlyRate = hourlyRate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "vehicleType=" + vehicleType +
                ", hourlyRate=" + hourlyRate +
                '}';
    }
}
