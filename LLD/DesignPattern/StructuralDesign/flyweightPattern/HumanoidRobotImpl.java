package DesignPattern.StructuralDesign.flyweightPattern;


public class HumanoidRobotImpl implements IRobot{

    String type;
    Sprite body;

    @Override
    public void display(int x, int y) {
        System.out.println("Displaying Humanoid Robot at coordinates: " + x + ", " + y);
    }

    HumanoidRobotImpl(String type, Sprite body){
     this.type = type;
     this.body = body;   
    }
    
}
