package DesignPattern.StructuralDesign.ProxyPattern;

public class ProxyDesignPattern {

    public static void main(String args[]) {
        try{
            Employee emp = new EmployeProxy();
            emp.createEmployee("ADMIN", new EmployeeData());
            System.out.println("Operation is successful");
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
