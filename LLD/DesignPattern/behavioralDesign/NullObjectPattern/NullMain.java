package DesignPattern.behavioralDesign.NullObjectPattern;

public class NullMain {

    public static void main(String args[]) {

        Vehical vehical = VehicalFactory.getVehicalObject("bike");
        printVehicalDetails(vehical);
    }

    private static void printVehicalDetails(Vehical vehical) {
        System.out.println("Tank Capacity: " + vehical.getTankCapacity());
        System.out.println("Sitting Capacity: " + vehical.getSittingCapacity());
    }
    
}
