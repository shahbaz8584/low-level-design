package DesignPattern.StructuralDesign.ProxyPattern;

public interface Employee {

    public EmployeeData getEmployeeInfo(String client, int empId) throws Exception;

    public void deleteByEmpId(String client, int empId) throws Exception;

    public void createEmployee(String client, EmployeeData data) throws Exception;

}
