package DesignPattern.StructuralDesign.bridgePattern.withPattern;

public class Tree extends LivingThing{

    private BreathingProcess breathingProcess;

    public Tree(BreathingProcess breathingProcess) {
        this.breathingProcess = breathingProcess;
    }

    @Override
    public void breathe() {
        breathingProcess.breathingProcess();
    }
    
}
