package DesignPattern.StructuralDesign.DecoratorDesign;

public class VanilaFlavorIceCream extends AbstractIcecream {

    @Override
    public String getDescription() {
        return "Vanila Flavor IceCream";
    }

    @Override
    public double cost() {
        return 50.0;
    }
    
}
