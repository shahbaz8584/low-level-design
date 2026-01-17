# Factory Pattern — Interview Reference

## Intent
Provide a method to create objects without exposing the instantiation logic, returning interface types to callers.

## Problem Statement
Client code directly depends on concrete classes, creating tight coupling. When new shape types are added, client code must change, violating Open/Closed Principle.

## Why Simple Code Fails
Scattering `new` constructors throughout the code and using `if-else` logic for type selection leads to:
- Tight coupling to concrete classes (Circle, Rectangle)
- Hard to add new shapes without modifying client code
- Creation logic scattered across multiple files
- Difficult to test (can't mock object creation)

## Solution Overview
Introduce a ShapeFactory that encapsulates shape creation. Client code depends on the factory and the Shape interface, not on concrete implementations.

## Participants / Roles
- ShapeInterface (abstract product interface)
- Circle, Rectangle (concrete products)
- ShapeFactory (creator/factory)
- Client (uses factory to get shapes)

## Runtime Execution Flow
1. Client calls `ShapeFactory.getShape(shapeType)`
2. Factory checks the shape type string
3. Factory instantiates appropriate concrete Shape
4. Factory returns Shape interface to client
5. Client calls methods on Shape interface (draw())

## Minimal Java Example

### Without Pattern

```java
// WITHOUT Factory - client directly instantiates shapes
public class ClientWithoutFactory {
  public static void main(String args[]) {
    // Tightly coupled to concrete classes!
    if (userInput.equals("circle")) {
      Shape s = new Circle();  // Depends on Circle
      s.draw();
    } else if (userInput.equals("rectangle")) {
      Shape s = new Rectangle();  // Depends on Rectangle
      s.draw();
    }
  }
}
```

### With Pattern

```java
// Product interface
public interface ShapeInterface {
  public void draw();
}

// Concrete products
public class Circle implements ShapeInterface {
  @Override
  public void draw() {
    System.out.println("Drawing Circle");
  }
}

public class Rectangle implements ShapeInterface {
  @Override
  public void draw() {
    System.out.println("Drawing Rectangle");
  }
}

// Factory
public class ShapeFactory {
  public ShapeInterface getShape(String shapeType) {
    if (shapeType == null) return null;
    
    if (shapeType.equals("circle")) {
      return new Circle();
    } else if (shapeType.equals("rectangle")) {
      return new Rectangle();
    }
    return null;
  }
}

// Client
public class Client {
  public static void main(String args[]) {
    ShapeFactory shapeFactory = new ShapeFactory();
    
    ShapeInterface shape1 = shapeFactory.getShape("circle");
    shape1.draw();
    
    ShapeInterface shape2 = shapeFactory.getShape("rectangle");
    shape2.draw();
  }
}

// Output:
// Drawing Circle
// Drawing Rectangle
```

## Advantages
- Isolates client from concrete class dependencies
- Centralizes object creation logic in one place
- Easy to add new shape types
- Follows Open/Closed Principle
- Improves code testability

## Disadvantages
- Adds extra layer of abstraction
- Factory class can grow large with many product types
- If-else logic can become hard to maintain
- May be overkill for few object types

## When NOT to Use
When you have only one product type or when object creation is trivial.

## Common Mistakes
- Putting business logic in factories
- Returning concrete types instead of interfaces
- Not making factory creation conditional

## Framework / Library Usage
- **java.util.Calendar**: `Calendar.getInstance()` returns specific calendar implementations
- **java.nio.file.Files**: `Files.newInputStream()` creates file input streams
- **Logging frameworks**: `LoggerFactory.getLogger()` creates logger instances
- **Database drivers**: `DriverManager.getConnection()` creates connections

## System Design Use Cases
- File format handlers (PDF, DOCX, PNG creation)
- Document parsers (JSON, XML, CSV parsing)
- UI component creation (Button, TextField, Dialog creation)
- Payment gateway adapters (PayPal, Stripe, Square)

## Interview One-Liner
Factory Pattern defines an interface for creating objects and lets subclasses or factory methods decide which concrete class to instantiate, centralizing creation logic and reducing client coupling.

## Common Interview Questions

### Q1: What's the difference between Factory Method and Factory Pattern?

**Answer:**
These terms are often used interchangeably, but technically:

| Aspect | Factory Method | Factory Pattern |
|--------|---|---|
| **Definition** | A method that creates objects | A design pattern using factory methods |
| **Scope** | Method-level | Class/object-level |
| **Usage** | Single factory method | Entire pattern with factory class |
| **Example** | `ShapeFactory.getShape()` | Entire ShapeFactory class |

The Factory Pattern typically refers to using a dedicated factory class or method to handle object creation.

---

### Q2: How would you add a new shape (Triangle) to the existing system?

**Answer:**
With Factory Pattern, adding a new shape is simple - just implement the interface and update the factory:

```java
// Step 1: Create new concrete product
public class Triangle implements ShapeInterface {
  @Override
  public void draw() {
    System.out.println("Drawing Triangle");
  }
}

// Step 2: Update factory to handle new type
public class ShapeFactory {
  public ShapeInterface getShape(String shapeType) {
    if (shapeType == null) return null;
    
    if (shapeType.equals("circle")) {
      return new Circle();
    } else if (shapeType.equals("rectangle")) {
      return new Rectangle();
    } else if (shapeType.equals("triangle")) {  // NEW!
      return new Triangle();
    }
    return null;
  }
}

// Step 3: Client code works without ANY changes!
ShapeFactory factory = new ShapeFactory();
ShapeInterface s = factory.getShape("triangle");
s.draw();  // Output: Drawing Triangle
```

**Key Point:** Client code doesn't need to change!

---

### Q3: What problems arise if you don't use Factory Pattern?

**Answer:**
Without the factory, you'd scatter shape creation throughout your code:

```java
// WITHOUT Factory - PROBLEMS!
public class ReportGenerator {
  public void generateReport(String shapeType) {
    // Creation logic scattered here
    if (shapeType.equals("circle")) {
      Shape s = new Circle();
    } else if (shapeType.equals("rectangle")) {
      Shape s = new Rectangle();
    }
    s.draw();
  }
}

public class GraphicsEditor {
  public void createShape(String shapeType) {
    // SAME logic repeated here!
    if (shapeType.equals("circle")) {
      Shape s = new Circle();
    } else if (shapeType.equals("rectangle")) {
      Shape s = new Rectangle();
    }
    s.draw();
  }
}
```

**Problems:**
1. **Code duplication** - Same if/else in multiple classes
2. **Hard to maintain** - Adding new shapes means changing multiple places
3. **Tight coupling** - All classes depend on concrete classes
4. **Single Responsibility violated** - Classes handle both creation and usage

**WITH Factory - CLEAN!**
```java
public class ReportGenerator {
  private ShapeFactory factory = new ShapeFactory();
  
  public void generateReport(String shapeType) {
    ShapeInterface s = factory.getShape(shapeType);
    s.draw();
  }
}
```

---

### Q4: How does Factory Pattern support the Open/Closed Principle?

**Answer:**
Factory Pattern is **OPEN for extension** but **CLOSED for modification**:

```java
// Better approach: use a Map for extensibility
public class ConfigurableShapeFactory {
  private static Map<String, ShapeInterface> shapeCache = new HashMap<>();
  
  static {
    shapeCache.put("circle", new Circle());
    shapeCache.put("rectangle", new Rectangle());
  }
  
  public ShapeInterface getShape(String type) {
    return shapeCache.get(type.toLowerCase());
  }
  
  // OPEN: Add new shape via registration
  public static void registerShape(String name, ShapeInterface shape) {
    shapeCache.put(name, shape);
  }
}

// Extending without modifying factory:
ConfigurableShapeFactory.registerShape("triangle", new Triangle());
ShapeInterface triangle = factory.getShape("triangle");  // Now works!
```

---

### Q5: Can Factory Pattern be combined with other patterns?

**Answer:**
Yes! Factory Pattern works well with other patterns:

**1. Factory + Singleton:**
```java
public class SingletonShapeFactory {
  private static SingletonShapeFactory instance;
  
  private SingletonShapeFactory() {}
  
  public static synchronized SingletonShapeFactory getInstance() {
    if (instance == null) {
      instance = new SingletonShapeFactory();
    }
    return instance;
  }
  
  public ShapeInterface getShape(String type) {
    // ... factory logic
  }
}

ShapeInterface shape = SingletonShapeFactory.getInstance().getShape("circle");
```

**2. Factory + Strategy:**
```java
public interface ShapeCreationStrategy {
  ShapeInterface createShape();
}

public class StrategyBasedShapeFactory {
  private Map<String, ShapeCreationStrategy> strategies = new HashMap<>();
  
  public ShapeInterface getShape(String type) {
    ShapeCreationStrategy strategy = strategies.get(type);
    return strategy.createShape();
  }
}
```

---

### Q6: How to avoid switch/if-else in factories?

**Answer:**
There are several approaches to eliminate if-else chains:

**Approach 1: Map-based Registry (Recommended)**
```java
public class RegistryShapeFactory {
  private static Map<String, Class<?>> registry = new HashMap<>();
  
  static {
    registry.put("circle", Circle.class);
    registry.put("rectangle", Rectangle.class);
    registry.put("triangle", Triangle.class);
  }
  
  public ShapeInterface getShape(String type) {
    try {
      Class<?> clazz = registry.get(type.toLowerCase());
      return (ShapeInterface) clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new IllegalArgumentException("Unknown shape: " + type);
    }
  }
  
  public static void registerShape(String name, Class<?> shapeClass) {
    registry.put(name, shapeClass);
  }
}

ShapeFactory factory = new RegistryShapeFactory();
ShapeInterface shape = factory.getShape("circle");
RegistryShapeFactory.registerShape("polygon", Polygon.class);
```

**Approach 2: Enum-based Factory**
```java
public enum ShapeType {
  CIRCLE(Circle::new),
  RECTANGLE(Rectangle::new),
  TRIANGLE(Triangle::new);
  
  private final Supplier<ShapeInterface> supplier;
  
  ShapeType(Supplier<ShapeInterface> supplier) {
    this.supplier = supplier;
  }
  
  public ShapeInterface create() {
    return supplier.get();
  }
}

ShapeInterface circle = ShapeType.CIRCLE.create();  // Type-safe!
```

---

### Q7: When choose Factory vs Builder?

**Answer:**
Factory and Builder solve different problems:

**Use Factory Pattern when:**
- Creating **simple objects** with few variations
- Need to **hide concrete class** details from client
- Creating objects **based on a type/condition**
- Object creation is **straightforward**

**Use Builder Pattern when:**
- Creating **complex objects** with many properties
- Object has **many optional parameters**
- Need to create **different representations** of same object
- Want to avoid **telescoping constructors**
- Need **immutable objects** with validation

**Comparison:**

| Aspect | Factory | Builder |
|--------|---------|---------|
| **Object Complexity** | Simple | Complex (many properties) |
| **Number of Parameters** | Few | Many (with optionals) |
| **Constructor Overloads** | 1-2 | Avoided |
| **Validation** | Optional | At build() time |
| **Immutability** | Not guaranteed | Can be guaranteed |
| **Creation Process** | One-shot | Step-by-step |

**Example:**
```java
// FACTORY: Simple type selection
public class DatabaseFactory {
  public Database createDatabase(String dbType) {
    switch(dbType) {
      case "mysql": return new MySQLDatabase();
      case "postgres": return new PostgresDatabase();
      case "mongodb": return new MongoDBDatabase();
      default: throw new IllegalArgumentException();
    }
  }
}

// BUILDER: Complex object construction
DatabaseConfig config = new DatabaseConfigBuilder()
  .host("db.example.com")
  .port(5432)
  .username("admin")
  .password("secret")
  .poolSize(20)
  .build();
```

**Key Insight:**
- **Factory** = "What type should I create?"
- **Builder** = "How should I construct this complex object?"

