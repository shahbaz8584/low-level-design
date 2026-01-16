package DesignPattern.creationalDesign.abstractFacotoryPattern;

public class LuxuryCarFactory implements CarFactory {

    public Car createCar() {
        return new LuxuryCar();
    }

    public Engine createEngine() {
        return new LuxuryEngine();
    }

    public Tyre createTyre() {
        return new LuxuryTyre();
    }
    
}
