package DesignPattern.creationalDesign.abstractFacotoryPattern;

public class CarShowroom {

    public static void main(String args[]) {

        CarFactory carFactory = new EconomyCarFactory();
        Car car = carFactory.createCar();
        Engine engine = carFactory.createEngine();
        Tyre tyre = carFactory.createTyre();
        car.assemble();
        engine.build();
        tyre.makeTyre();
        

    }
    
}
