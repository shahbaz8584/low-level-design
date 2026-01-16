package DesignPattern.StructuralDesign.flyweightPattern;

import javax.swing.Spring;

public class RoboticDogImpl implements IRobot{

    String type;
    Sprite body;

    RoboticDogImpl(String type, Sprite body)
    {
     this.type = type;
     this.body = body;   
    }

    @Override
    public void display(int x, int y) { 
        System.out.println("Displaying Robotic Dog at coordinates: " + x + ", " + y);
    }
    
}
