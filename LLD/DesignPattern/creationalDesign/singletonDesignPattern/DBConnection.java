package DesignPattern.creationalDesign.singletonDesignPattern;

public class DBConnection {

    private static DBConnection dbConnectionInstance; 
    //= new DBConnection();

    private DBConnection() {
        
    }

    // Eager Initialization
    public static DBConnection getInstance() {
        return dbConnectionInstance;
    }

    // Lazy Initialization
    public static DBConnection getDBConnectionInstance() {
        if(dbConnectionInstance == null) {
            dbConnectionInstance = new DBConnection();
        }
        return dbConnectionInstance;
    }

    //Synchronized Method
    public static synchronized DBConnection getSynchronizedDBConnectionInstance() {
        if(dbConnectionInstance == null) {
            dbConnectionInstance = new DBConnection();
        }
        return dbConnectionInstance;
    }

    // Double Checked Locking
    public static DBConnection getEagerDBConnectionInstance() {
        if(dbConnectionInstance == null) {
            synchronized (DBConnection.class) {
                if(dbConnectionInstance == null) {
                    dbConnectionInstance = new DBConnection();
                }
            }
        }
        return dbConnectionInstance;
    }
    
}
