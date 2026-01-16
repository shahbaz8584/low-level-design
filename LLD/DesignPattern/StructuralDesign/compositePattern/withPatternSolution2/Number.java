package DesignPattern.StructuralDesign.compositePattern.withPatternSolution2;

public class Number implements ArthematicExpression {
    
    int value;

    public Number(int value){
        this.value = value;
    }

    @Override
    public int evaluate(){
        System.out.println("NUmber value: " + value);
        return value;
    }
    
}
