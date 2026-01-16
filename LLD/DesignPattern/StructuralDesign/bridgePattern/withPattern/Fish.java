package DesignPattern.StructuralDesign.bridgePattern.withPattern;

public class Fish extends LivingThing{

    private BreathingProcess breathingProcess;

    public Fish(BreathingProcess breathingProcess){
        this.breathingProcess = breathingProcess;
    }

    @Override
    public void breathe() {
        breathingProcess.breathingProcess();
    }
    
}
