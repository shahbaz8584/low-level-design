# Singleton - Class Diagram

```mermaid
classDiagram
    class DBConnection {
        -instance: DBConnection static
        -connection: Connection private
        -DBConnection() private
        +getInstance()✨ DBConnection static
        +executeQuery(String) void
        +closeConnection() void
    }
    
    note for DBConnection "✨ Only ONE instance exists\n🔒 Private constructor\n🌍 Global access point"
```

## Visual Flow Diagram
```mermaid
sequenceDiagram
    participant Client
    participant DBConnection
    
    Note over Client,DBConnection: First Call
    Client->>DBConnection: getInstance()
    activate DBConnection
    DBConnection->>DBConnection: Check if instance == null
    DBConnection->>DBConnection: Create new instance 🔧
    deactivate DBConnection
    
    Note over Client,DBConnection: Subsequent Calls
    Client->>DBConnection: getInstance()
    activate DBConnection
    DBConnection->>DBConnection: instance already exists
    DBConnection-->>Client: Return existing instance
    deactivate DBConnection
```

## Class Relationships

| Class | Responsibility | Depends On |
|-------|---|---|
| **DBConnection** | Singleton - only one instance, global access | Self-manages instance |

## How to Code This Pattern

### Eager Initialization (Thread-Safe)
```java
class DBConnection {
    private static final DBConnection instance = new DBConnection();
    
    private DBConnection() { }
    
    public static DBConnection getInstance() {
        return instance;
    }
}
```

### Lazy Initialization (Thread-Safe)
```java
class DBConnection {
    private static DBConnection instance;
    
    private DBConnection() { }
    
    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
}
```

### Double-Checked Locking
```java
class DBConnection {
    private static volatile DBConnection instance;
    
    private DBConnection() { }
    
    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized(DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }
}
```

## Key Points

- **Private Constructor**: Prevent instantiation from outside
- **Static Instance**: Hold single instance
- **getInstance()**: Return the singleton instance
- **Thread-Safe**: Use synchronized or volatile for concurrency
- **Enum Alternative**: Use enum for automatic singleton
