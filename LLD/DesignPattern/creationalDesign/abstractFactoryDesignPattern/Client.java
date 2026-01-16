package DesignPattern.creationalDesign.abstractFactoryDesignPattern;

public class Client {

    public static void main(String args[]) {
        Employee e1 = EmployeeFactory.getEmployee(new WebDeveloperFactory());
        e1.name();

        Employee e2 = EmployeeFactory.getEmployee(new AndroidDeveloperFactory());
        e2.name();
        };
    }
