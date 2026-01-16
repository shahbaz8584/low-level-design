package DesignPattern.DesignPatternQuestions.parkinglot;

public class Main {
    public static void main(String[] args) {
        // Get the singleton instance of ParkingLot
        ParkingLot parkingLot = ParkingLot.getInstance();

        // Add levels to the parking lot
        Level level1 = new Level(1, 5, 10, 3); // 5 motorcycle spots, 10 car spots, 3 truck spots
        Level level2 = new Level(2, 5, 10, 3);
        Level level3 = new Level(3, 5, 10, 3);

        parkingLot.addLevel(level1);
        parkingLot.addLevel(level2);
        parkingLot.addLevel(level3);

        System.out.println("Parking Lot initialized with " + parkingLot.getTotalSpots() + " total spots.");

        // Display initial parking lot status
        parkingLot.displayParkingLot();

        // Create vehicles
        Vehicle car1 = new Car("KA-01-AB-1234", "Red", "Honda Accord");
        Vehicle car2 = new Car("KA-01-AB-1235", "Blue", "Toyota Camry");
        Vehicle motorcycle1 = new Motorcycle("KA-01-AB-5678", "Black", "Honda CB Shine");
        Vehicle truck1 = new Truck("KA-01-AB-9999", "White", "Ashok Leyland");

        // Park vehicles
        System.out.println("\n=== Parking Vehicles ===");
        ParkingTicket ticket1 = parkingLot.parkVehicle(car1);
        ParkingTicket ticket2 = parkingLot.parkVehicle(car2);
        ParkingTicket ticket3 = parkingLot.parkVehicle(motorcycle1);
        ParkingTicket ticket4 = parkingLot.parkVehicle(truck1);

        parkingLot.displayParkingLot();

        // Try to park in a full level scenario
        System.out.println("\n=== Attempting to park another vehicle ===");
        Vehicle car3 = new Car("KA-01-AB-1236", "Green", "Maruti Swift");
        ParkingTicket ticket5 = parkingLot.parkVehicle(car3);

        // Simulate time passing
        System.out.println("\n=== Unparking Vehicles ===");
        try {
            // Simulate parking duration
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        parkingLot.unparkVehicle("KA-01-AB-1234");
        parkingLot.displayParkingLot();

        parkingLot.unparkVehicle("KA-01-AB-5678");
        parkingLot.displayParkingLot();

        // Process payment
        System.out.println("\n=== Processing Payment ===");
        parkingLot.processPayment("KA-01-AB-1234");
        parkingLot.processPayment("KA-01-AB-5678");

        // Display final status
        parkingLot.displayParkingLot();

        System.out.println("\nParking Lot has " + parkingLot.getTotalAvailableSpots() + " available spots out of " + parkingLot.getTotalSpots());
    }
}
