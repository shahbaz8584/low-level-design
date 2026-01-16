package DesignPattern.StructuralDesign.flyweightPattern;

public class Main {

    public static void main(String args[]) {
        IRobot humanoidRobot = RoboticFactory.createRobot("Humanoid");
        humanoidRobot.display(10, 20);

        IRobot roboticDog = RoboticFactory.createRobot("RoboticDog");
        roboticDog.display(30, 40);

    }
    
}
