package DesignPattern.creationalDesign.abstractFactoryDesignPattern;

public class EmployeeFactory {

    public static Employee getEmployee(EmployeeAbstractFactory factory){
        return factory.createEmployee();
    }
    
}
