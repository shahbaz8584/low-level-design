package DesignPattern.creationalDesign.factoryPattern;

import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the shape you want to draw (CIRCLE/RECTANGLE): ");
        String shapeType = sc.nextLine();
        shapeType = shapeType.toLowerCase();
        ShapeFactory ShapeFactory = new ShapeFactory();
        ShapeInterface shape = ShapeFactory.getShape(shapeType);
        System.out.println("Created Shape: ");
        shape.draw();

    }
    
}
