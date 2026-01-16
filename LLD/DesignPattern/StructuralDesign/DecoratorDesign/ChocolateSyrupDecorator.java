package DesignPattern.StructuralDesign.DecoratorDesign;

public class ChocolateSyrupDecorator extends AbstarctIceCreamDecorator {

    AbstractIcecream icecream;

    public ChocolateSyrupDecorator(AbstractIcecream icecream) {
        this.icecream = icecream;
    }

    @Override
    public String getDescription() {
        return icecream.getDescription() + ", Chocolate Syrup";
    }

    @Override
    public double cost() {
        return icecream.cost() + 30.0;
    }
    
}
