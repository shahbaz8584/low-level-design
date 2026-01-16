package DesignPattern.creationalDesign.abstractFactoryDesignPattern;

public class WebDeveloper implements Employee {

    @Override
    public int getSalary() {
        return 12000;
    }

    @Override
    public String name() {
        return "Web Developer";
    }
    
}
