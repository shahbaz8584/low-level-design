package DesignPattern.StructuralDesign.compositePattern.withPatternSolution2;

public class Main {

    public static void main(String args[]) {
        ArthematicExpression expr1 = new Number(10);
        ArthematicExpression expr2 = new Number(5);
        ArthematicExpression expr3 = new Number(6);
        ArthematicExpression addition = new Expression(expr2, expr3, ExpressionEnum.ADD);
        ArthematicExpression multiplication = new Expression(addition, expr1, ExpressionEnum.MULTIPLY);
        multiplication.evaluate();

    }
    
}
