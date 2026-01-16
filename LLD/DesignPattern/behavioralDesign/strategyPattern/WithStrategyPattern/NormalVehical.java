package DesignPattern.behavioralDesign.strategyPattern.WithStrategyPattern;

import DesignPattern.behavioralDesign.strategyPattern.WithStrategyPattern.Strategy.NormalDrive;

public class NormalVehical extends Vehical {

    public NormalVehical() {
        super(new NormalDrive());
    }
    
}
