# Singleton Pattern — Interview Reference

## Intent
Ensure a class has only one instance and provide a global access point.

## Problem Statement
Multiple parts of a system require a single shared resource/configuration; naive globals lead to uncontrolled instantiation and state issues.

## Why Simple Code Fails
Using public static fields or unguarded lazy init leads to thread-safety bugs and testing difficulties.

## Solution Overview
Expose a single instance through a controlled access method; handle lazy initialization safely and consider dependency injection alternatives.

## UML Diagram
See `UML/ClassDiagram.md` and generated diagram at `build/diagrams/creationalDesign_singletonDesignPattern_UML_ClassDiagram.md.png` (if present).

## Participants / Roles
- Singleton (the single instance holder)

## Runtime Execution Flow
1. Client requests Singleton.getInstance()
2. getInstance() either returns existing instance or initializes it safely
3. Client uses shared instance

## Minimal Java Example
## Without Pattern

```java
// Without Singleton: using public static field or multiple instances
Config a = new Config();
Config b = new Config();
```

## With Pattern

```java
// With Singleton: single shared instance
Config cfg = Config.getInstance();
```


```java
public class Config {
  private static volatile Config instance;
  private Config(){}
  public static Config getInstance(){
    if(instance==null){
      synchronized(Config.class){
        if(instance==null) instance = new Config();
      }
    }
    return instance;
  }
}
```

## Advantages
- Controlled access to single instance
- Useful for shared resources

## Disadvantages
- Global state hampers testability
- Can hide dependencies and increase coupling

## When NOT to Use
- When DI can provide better lifecycle and scoping
- For per-request or short-lived objects

## Common Mistakes
- Not handling thread-safety
- Using Singleton as a catch-all global

## Framework / Library Usage
- Use dependency injection (Spring beans with singleton scope) rather than hand-rolled singletons where possible.

## System Design Use Cases
- Configuration managers, caches, connection pools (careful: pools are better as separate objects), logging facades

## Interview One-Liner
Singleton ensures a single instance and global access, but can introduce global state and testability problems.

## Common Interview Questions

### Q1: How to write thread-safe lazy singleton in Java?

**Answer:** There are multiple approaches with different trade-offs:

**1. Double-Checked Locking (Most Common):**
```java
public class DBConnection {
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

**2. Bill Pugh Singleton (Best - Uses Class Loader):**
```java
public class DBConnection {
    private DBConnection() { }
    
    private static class SingletonHelper {
        private static final DBConnection INSTANCE = new DBConnection();
    }
    
    public static DBConnection getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
```

**3. Enum Singleton (Most Bulletproof):**
```java
public enum DBConnection {
    INSTANCE;
    
    public void query(String sql) {
        // Implementation
    }
}

// Usage
DBConnection.INSTANCE.query("SELECT *");
```

### Q2: Why is 'volatile' required in double-checked locking?

**Answer:** Without `volatile`, the second check `instance == null` might see partially initialized object due to reordering:

```java
// BAD - without volatile
private static DBConnection instance;

public static DBConnection getInstance() {
    if (instance == null) {  // Thread A sees partially initialized object!
        synchronized(DBConnection.class) {
            if (instance == null) {
                instance = new DBConnection(); // Not fully constructed yet
            }
        }
    }
    return instance;
}

// GOOD - with volatile ensures visibility
private static volatile DBConnection instance;

// volatile forces:
// 1. Memory barrier - all writes visible to all threads
// 2. Prevent instruction reordering
```

### Q3: When are singletons harmful?

**Answer:** Singletons create problems despite simplicity:

- **Global state**: Hidden dependency - hard to track who modifies it
- **Testing nightmare**: Can't mock singletons, state carries between tests
- **Thread-safety**: Complex synchronized code hard to maintain
- **Cannot subclass**: Final to prevent multiple instances
- **Lifetime control**: Can't control creation/destruction
- **Hidden coupling**: Classes implicitly depend on global singleton

**Example of problems:**
```java
// Hard to test - can't replace with mock
DatabaseConnection.getInstance().query(...);

// State pollution between tests
@Test void test1() {
    cache.put("key", "value");
}

@Test void test2() {
    assertEquals("value", cache.get("key")); // Fails if test1 ran first!
}
```

### Q4: What are alternatives to Singleton?

**Answer:**
```java
// 1. Dependency Injection (PREFERRED)
public class Service {
    private DatabaseConnection db;
    
    public Service(DatabaseConnection db) { // Inject dependency
        this.db = db;
    }
}

// 2. Static Factory Method
public class Config {
    public static Config getInstance() {
        return new Config(); // Appears like singleton but testable
    }
}

// 3. Service Locator (Anti-pattern but alternative)
ServiceLocator.register("db", new DatabaseConnection());
DatabaseConnection db = ServiceLocator.get("db");

// 4. ThreadLocal (For thread-scoped singletons)
public class RequestContext {
    private static ThreadLocal<RequestContext> context = new ThreadLocal<>();
    
    public static RequestContext getInstance() {
        RequestContext ctx = context.get();
        if (ctx == null) {
            ctx = new RequestContext();
            context.set(ctx);
        }
        return ctx;
    }
}
```

### Q5: How is eager initialization different from lazy initialization?

**Answer:**
| Aspect | Eager | Lazy |
|--------|-------|------|
| **When created** | JVM startup (class loading) | First call to getInstance() |
| **Thread-safe** | Always (class loader handles) | Must synchronize |
| **Startup time** | Slower (create all singletons upfront) | Faster (create on demand) |
| **Memory** | Uses memory immediately | Uses memory only when needed |
| **Exceptions** | Throw during startup (fail-fast) | Throw at first access (bad) |

**Code:**
```java
// Eager - created when class loads
public class Config {
    private static final Config INSTANCE = new Config();
    
    public static Config getInstance() {
        return INSTANCE;
    }
}

// Lazy - created first time getInstance() called
public class Config {
    private static Config instance;
    
    public static synchronized Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }
}
```

### Q6: How does Singleton behave with reflection/serialization?

**Answer:** Reflection and serialization can break singleton:

```java
// PROBLEM 1: Reflection creates new instance
DBConnection original = DBConnection.getInstance();
Constructor<?> constructor = DBConnection.class.getDeclaredConstructor();
constructor.setAccessible(true);
DBConnection fake = (DBConnection) constructor.newInstance(); // NEW INSTANCE!

// Solution: Throw exception in constructor
private DBConnection() {
    if (instance != null) {
        throw new IllegalStateException("Singleton already exists");
    }
}

// PROBLEM 2: Serialization creates new instance on deserialization
ObjectInputStream in = new ObjectInputStream(file);
DBConnection deserialized = (DBConnection) in.readObject(); // NEW INSTANCE!

// Solution: Override readResolve()
public Object readResolve() {
    return getInstance(); // Return existing instance
}

// Solution: Use Enum (handles both automatically)
public enum DBConnection {
    INSTANCE;
}
```

### Q7: Should you use Singleton in microservices?

**Answer:** Generally NO - different context:

```java
// Monolith - ONE JVM = ONE instance
Config cfg = Config.getInstance(); // Makes sense

// Microservices - MULTIPLE instances per service (horizontal scaling)
// Replica 1: has Config.INSTANCE
// Replica 2: has different Config.INSTANCE (different memory!)
// Not truly "single"

// Better approaches for microservices:
// 1. Dependency Injection with container lifecycle
@Bean(scope = "singleton")
public Config config() {
    return new Config();
}

// 2. Configuration Server (shared config for all instances)
Configuration config = configServer.getConfig("database");

// 3. Distributed Cache (Redis, Memcached)
Cache cache = distributedCache.get("singleton-key");
```
