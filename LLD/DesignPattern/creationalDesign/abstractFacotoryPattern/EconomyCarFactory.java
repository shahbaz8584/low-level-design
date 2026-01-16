package DesignPattern.creationalDesign.abstractFacotoryPattern;

public class EconomyCarFactory implements CarFactory {

    public Car createCar() {
        return new EconomyCar();
    }

    public Engine createEngine() {
        return new EconomyEngine();
    }

    public Tyre createTyre() {
        return new EconomyTyre();
    }
    
}
