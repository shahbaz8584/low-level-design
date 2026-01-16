package DesignPattern.behavioralDesign.NullObjectPattern;

public class VehicalFactory {

    static Vehical getVehicalObject(String vehicalType) {

        if("Car".equals(vehicalType)){
            return new Car();
        }
        return new NullVehical(); 
    }
    
}
