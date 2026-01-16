package DesignPattern.behavioralDesign.strategyPattern.WithStrategyPattern;

import DesignPattern.behavioralDesign.strategyPattern.WithStrategyPattern.Strategy.StrategyPattern;

public class Vehical {

    private StrategyPattern strategy;

    public Vehical(StrategyPattern strategy){
        this.strategy = strategy;
    }

    public void drive(){
        strategy.drive();
    }

}
