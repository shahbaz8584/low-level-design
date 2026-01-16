package DesignPattern.behavioralDesign.objectPoolPattern;

public class Client {

    public static void main(String args[]) {
        DBConnectionPoolManager poolManager = DBConnectionPoolManager.getInstance();

        DBConnection connection1 = poolManager.getPoolConnection();
        DBConnection connection2 = poolManager.getPoolConnection();
        DBConnection connection3 = poolManager.getPoolConnection();
        DBConnection connection4 = poolManager.getPoolConnection();
        DBConnection connection5 = poolManager.getPoolConnection();
        DBConnection connection6 = poolManager.getPoolConnection();
        DBConnection connection7 = poolManager.getPoolConnection();
        // DBConnection connection8 = poolManager.getPoolConnection();
        // DBConnection connection9 = poolManager.getPoolConnection();
        // DBConnection connection10 = poolManager.getPoolConnection();
        poolManager.getInstance();
        poolManager.releaseDBConnection(connection6);

    }
    
}
