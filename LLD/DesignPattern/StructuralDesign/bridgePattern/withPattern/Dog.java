package DesignPattern.StructuralDesign.bridgePattern.withPattern;

public class Dog extends LivingThing{

    private BreathingProcess breathingProcess;

    public Dog(BreathingProcess breathingProcess){
        this.breathingProcess = breathingProcess;
    }

    @Override
    public void breathe() {
        breathingProcess.breathingProcess();
    }


    
}
