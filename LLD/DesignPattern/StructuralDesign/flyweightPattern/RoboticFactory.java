package DesignPattern.StructuralDesign.flyweightPattern;

import java.util.HashMap;
import java.util.Map;

public class RoboticFactory {

    static Map<String, IRobot> roboticMap = new HashMap<>();

    public static IRobot createRobot(String type){
        if(roboticMap.containsKey(type)){
            return roboticMap.get(type);
        }
        if(type.equals("Humanoid")) {
            Sprite HumanoidSprite = new Sprite();
            IRobot humanoidRobot = new HumanoidRobotImpl("Humanoid", HumanoidSprite);
            roboticMap.put("Humanoid", humanoidRobot);
            return humanoidRobot;
        }
        else if(type.equals("RoboticDog")){
            Sprite RoboticDogSprite = new Sprite();
            IRobot roboticDog = new RoboticDogImpl("RoboticDog", RoboticDogSprite);
            roboticMap.put("RoboticDog", roboticDog);
            return roboticDog;
        }

        return null;
    }
    
}
