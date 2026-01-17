package DesignPattern.creationalDesign.abstractFactoryDesignPattern;

public class AndroidDeveloper implements Employee {

    @Override
    public int getSalary() {
        return 15000;
    }

    @Override
    public String name() {
        return "Android Developer";
    }
    
}
