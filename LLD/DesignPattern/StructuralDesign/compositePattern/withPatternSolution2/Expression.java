package DesignPattern.StructuralDesign.compositePattern.withPatternSolution2;

public class Expression implements ArthematicExpression{

    ArthematicExpression leftExpression;
    ArthematicExpression rightExpression;
    ExpressionEnum operation;
    

    public Expression(ArthematicExpression leftExpression, ArthematicExpression rightExpression, ExpressionEnum operation) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operation = operation;
    }

    @Override
    public int evaluate() {
        int value = 0;
        switch(operation) {
            case ADD:
                value = leftExpression.evaluate() + rightExpression.evaluate();
                break;
            case SUBTRACT:
                value = leftExpression.evaluate() - rightExpression.evaluate();
                break;
            case MULTIPLY:
                value = leftExpression.evaluate() * rightExpression.evaluate();
                break;
            case DIVIDE:
                value = leftExpression.evaluate() / rightExpression.evaluate();    
                break;
    }
    System.out.println("Expression value: " + value);
    return value;
    }
    
}
