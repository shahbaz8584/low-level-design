package DesignPattern.behavioralDesign.NullObjectPattern;

public class NullVehical implements Vehical {

    @Override
    public int getTankCapacity() {
        return 0;
    }

    @Override
    public int getSittingCapacity() {
        return 0;
    }
    
}
