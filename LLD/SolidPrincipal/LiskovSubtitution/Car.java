package LiskovSubtitution;

public class Car extends VehicleEngine{

    public boolean hasEngine() {
        return true;
    }

    @Override
    public int noOfWheels() {
        return 4;   
    }
}
