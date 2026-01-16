package DesignPattern.StructuralDesign.bridgePattern.withPattern;

public class Client {

    public static void main(String args[]) {
        
        LivingThing tree = new Tree(new PhotoSynthesisProcess());
        tree.breathe();
    }
    
}
