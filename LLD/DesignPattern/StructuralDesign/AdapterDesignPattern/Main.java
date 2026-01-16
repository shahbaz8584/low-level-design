package DesignPattern.StructuralDesign.AdapterDesignPattern;

public class Main {

    public static void main(String args[]){
        WeightMachine weightMachine = new WeightMachineOfInfantImpl();
        WeightMachineAdapter adapter = new WeightMachineAdapterImpl(weightMachine);
        System.out.println("Weight in Kg: " + adapter.getWeightInKg());
        

        // WeightMachineAdapter adapter = new WeightMachineAdapterImpl(new WeightMachineOfInfantImpl());
        // System.out.println("Weight in Kg: " + adapter.getWeightInKg());
    }
    
}
