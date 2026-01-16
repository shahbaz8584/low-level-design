package DesignPattern.StructuralDesign.DecoratorDesign;

public class IceCreamShop {

    public static void main(String args[]){

        AbstractIcecream icecream = new VanilaFlavorIceCream();
        icecream = new ChocoChipsDecorator(new ChocolateSyrupDecorator(icecream));
        printInvoice(icecream);
    }

    private static void printInvoice(AbstractIcecream icecream){
        System.out.println("Description: " + icecream.getDescription());
        System.out.println("Cost: " + icecream.cost());
    }
    
}
