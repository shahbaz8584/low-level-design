package DesignPattern.behavioralDesign.NullObjectPattern;

public class Car implements Vehical {

    @Override
    public int getTankCapacity() {
        return 50;
    }
    
    @Override
    public int getSittingCapacity() {
        return 5;
    }
}
