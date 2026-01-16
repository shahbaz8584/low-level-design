package DesignPattern.behavioralDesign.objectPoolPattern;

import java.util.ArrayList;
import java.util.List;



public class DBConnectionPoolManager {

    List<DBConnection> freeResourceList = new ArrayList<>();
    List<DBConnection> inUseList = new ArrayList<>();
    int INITIAL_POOL_SIZE = 3;
    int MAX_POOL_SIZE = 6;
    private static DBConnectionPoolManager poolConnection = null;

    private DBConnectionPoolManager(){
        for(int i = 0; i < INITIAL_POOL_SIZE; i++) {
            freeResourceList.add(new DBConnection());
        }
    }

    public static DBConnectionPoolManager getInstance(){
        // if(freeResourceList.isEmpty() && inUseList.size() < MAX_POOL_SIZE) {
        //     freeResourceList.add(new DBConnection());
        //     System.out.println("Creating a new connection and putting into pool, free pool size: " + freeResourceList.size());
        // }
        // else if(freeResourceList.isEmpty() && inUseList.size() >= MAX_POOL_SIZE){
        //     System.out.println(" No Free pool size available");
        //     return null;
        // }
        // DBConnection dbConnection = freeResourceList.remove(freeResourceList.size() - 1);
        // inUseList.add(dbConnection);
        // return null;
        if(poolConnection == null) {
            synchronized(DBConnectionPoolManager.class){
                if(poolConnection == null) {
                    poolConnection = new DBConnectionPoolManager();
                }
            }
        }
        return poolConnection;
    }   
    
    // public void releaseDBConnection(DBConnection connection) {
    //     if(connection != null) {
    //         inUseList.remove(connection);
    //         System.out.println("Removing db connection from use pool, size " + inUseList.size());
    //         freeResourceList.add(connection);
    //         System.out.println("Adding DB Conneciton into free pool size, " + freeResourceList.size());
    //     }
    // }

    public synchronized void releaseDBConnection(DBConnection dbConnection){
        if(dbConnection != null){
            inUseList.remove(dbConnection);
            freeResourceList.add(dbConnection);
        }
    }

    public synchronized DBConnection getPoolConnection(){
        if(freeResourceList.isEmpty() && inUseList.size() < MAX_POOL_SIZE) {
            freeResourceList.add(new DBConnection());
        }
        else if(freeResourceList.isEmpty() && inUseList.size() >= MAX_POOL_SIZE){
            System.out.println("No Free pool size available");
            return null;
        }
        DBConnection dbConnection = freeResourceList.remove(freeResourceList.size() - 1);
        inUseList.add(dbConnection);
        return dbConnection;
    }
}
