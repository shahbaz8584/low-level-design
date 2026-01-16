package DesignPattern.creationalDesign.abstractFacotoryPattern;

public interface CarFactory {

    Car createCar();
    
    Engine createEngine();
    
    Tyre createTyre();
    
}
