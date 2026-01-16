package DesignPattern.StructuralDesign.DecoratorDesign;

public class ChocolateFlavorIceCream extends AbstractIcecream {

    @Override
    public String getDescription() {
        return "Chocolate Flavor IceCream";
    }

    @Override
    public double cost() {
        return 70.0;
    }
    
}
