package DesignPattern.StructuralDesign.ProxyPattern;

public class EmployeeImpl implements Employee{

    @Override
    public EmployeeData getEmployeeInfo(String client, int empId) throws Exception{
        System.out.println("Getting Employee Info for empId: " + empId + " for client: " + client);
        return new EmployeeData();
    }

    @Override
    public void deleteByEmpId(String client, int empId) throws Exception{
        System.out.println("Deleting Employee Info for empId: " + empId + " for client: " + client);
    }

    @Override
    public void createEmployee(String client, EmployeeData data) throws Exception{
        System.out.println("Creating Employee Info for client: " + client);
    }

}
