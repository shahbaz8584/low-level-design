package DesignPattern.behavioralDesign.strategyPattern.WithStrategyPattern;

import DesignPattern.behavioralDesign.strategyPattern.WithStrategyPattern.Strategy.SportsDrive;

public class SportsVehical extends Vehical {

    public SportsVehical() {
        super(new SportsDrive());
    }
    
}
