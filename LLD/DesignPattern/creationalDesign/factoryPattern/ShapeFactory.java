package DesignPattern.creationalDesign.factoryPattern;


public class ShapeFactory {


    public ShapeInterface getShape(String shapeType) {
        ShapeInterface shape = null;
        if (shapeType == null) {
            return null;
        } else if (shapeType.equalsIgnoreCase("CIRCLE")) {
            shape = new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            shape = new Rectangle();
        }
        return shape;
    }
}
