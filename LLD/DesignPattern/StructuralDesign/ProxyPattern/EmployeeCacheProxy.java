package DesignPattern.StructuralDesign.ProxyPattern;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmployeeCacheProxy implements Employee {
    private Employee employee;
    private final Map<Integer, EmployeeData> cache = new ConcurrentHashMap<>();
    private final RedisCacheClient redisClient;

    public EmployeeCacheProxy() {
        this.employee = new EmployeeImpl();
        this.redisClient = new RedisCacheClient(); // skeleton - implement if Redis libs are available
    }

    @Override
    public EmployeeData getEmployeeInfo(String client, int empId) throws Exception {
        // Try Redis first (if implemented)
        try {
            EmployeeData data = redisClient.get(empId);
            if (data != null) {
                System.out.println("Cache hit (Redis) for empId: " + empId);
                return data;
            }
        } catch (UnsupportedOperationException ignore) {
            // Redis not configured - fall back to in-memory cache
        }

        // In-memory cache
        EmployeeData data = cache.get(empId);
        if (data != null) {
            System.out.println("Cache hit (in-memory) for empId: " + empId);
            return data;
        }

        // Cache miss - delegate to real implementation
        System.out.println("Cache miss for empId: " + empId + ", delegating to real service");
        data = employee.getEmployeeInfo(client, empId);
        if (data != null) {
            cache.put(empId, data);
            try {
                redisClient.set(empId, data);
            } catch (UnsupportedOperationException ignore) {
            }
        }
        return data;
    }

    @Override
    public void deleteByEmpId(String client, int empId) throws Exception {
        employee.deleteByEmpId(client, empId);
        cache.remove(empId);
        try {
            redisClient.del(empId);
        } catch (UnsupportedOperationException ignore) {
        }
    }

    @Override
    public void createEmployee(String client, EmployeeData data) throws Exception {
        employee.createEmployee(client, data);
        // Cannot determine empId here to cache; caller should fetch to populate cache
    }
}
