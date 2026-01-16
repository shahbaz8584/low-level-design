package DesignPattern.StructuralDesign.ProxyPattern;

public class EmployeProxy implements Employee{
    private Employee employee;

    EmployeProxy() {
        this.employee = new EmployeeImpl();
    }

    @Override
    public EmployeeData getEmployeeInfo(String client, int empId)throws Exception {
        if(client.equals("ADMIN")) {
            return employee.getEmployeeInfo(client, empId);
        }
        throw new UnsupportedOperationException("Access Denied");
    }

    @Override
    public void deleteByEmpId(String client, int empId)throws Exception {
        if(client.equals("ADMIN")) {
            employee.deleteByEmpId(client, empId);
            return;
        }
        throw new UnsupportedOperationException("Access Denied");
    }

    @Override
    public void createEmployee(String client, EmployeeData data) throws Exception{
        if(client.equals("ADMIN")) {
            employee.createEmployee(client, data);
            return;
        }
        throw new UnsupportedOperationException("Access Denied");
    }
    
    
}
