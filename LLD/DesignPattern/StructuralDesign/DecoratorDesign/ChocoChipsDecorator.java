package DesignPattern.StructuralDesign.DecoratorDesign;

public class ChocoChipsDecorator extends AbstarctIceCreamDecorator {

    AbstractIcecream icecream;
    
    public ChocoChipsDecorator(AbstractIcecream icecream) {
        this.icecream = icecream;
    }

    @Override
    public String getDescription() {
        return icecream.getDescription() + " with Choco Chips";
    }

    @Override
    public double cost() {
        return icecream.cost() + 20.0;
    }
}
