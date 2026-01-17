# Builder Pattern — Interview Reference

## Intent
Separate the construction of a complex object from its representation, allowing the same construction process to create different representations.

## Problem Statement
Objects with many optional parameters and complex initialization logic become hard to construct. Multiple constructor overloads (telescoping constructor anti-pattern) or setters that leave objects in invalid states lead to error-prone code and maintenance nightmares.

## Why Simple Code Fails
Telescoping constructors and setter methods create several problems:
- Too many constructor overloads with different parameter combinations
- Objects can be left in partially initialized invalid states
- Hard to distinguish between required and optional parameters
- Code becomes unreadable with many parameters

## Solution Overview
Introduce a Builder class that encapsulates step-by-step construction. The builder provides a fluent interface for setting properties and a final `build()` method that creates the immutable object with validation.

## Participants / Roles
- Product (complex object being constructed)
- Builder (specifies steps to construct the product)
- ConcreteBuilder (implements builder interface)
- Director (optional - orchestrates builder steps)

## Runtime Execution Flow
1. Client creates a Builder instance
2. Client calls builder methods to set properties (fluent interface)
3. Client calls build() to construct the final Product
4. Builder validates state and returns immutable Product

## Minimal Java Example
## Without Pattern

```java
// Telescoping constructor anti-pattern
public class House {
  public House(String walls) { ... }
  public House(String walls, String roof) { ... }
  public House(String walls, String roof, String windows) { ... }
  public House(String walls, String roof, String windows, String doors) { ... }
  // 20+ more overloads...
}

// Usage is confusing
House h = new House("brick", "tiles", "glass", "wood");
```

## With Pattern

```java
// Builder pattern with fluent interface
public class House {
  private String walls;
  private String roof;
  private String windows;
  private String doors;
  
  private House(Builder builder) {
    this.walls = builder.walls;
    this.roof = builder.roof;
    this.windows = builder.windows;
    this.doors = builder.doors;
  }
  
  public static class Builder {
    private String walls = "brick";
    private String roof = "tiles";
    private String windows = "glass";
    private String doors = "wood";
    
    public Builder walls(String walls) { 
      this.walls = walls; 
      return this; 
    }
    public Builder roof(String roof) { 
      this.roof = roof; 
      return this; 
    }
    public Builder windows(String windows) { 
      this.windows = windows; 
      return this; 
    }
    public Builder doors(String doors) { 
      this.doors = doors; 
      return this; 
    }
    
    public House build() {
      return new House(this);
    }
  }
}

// Usage - clear and readable
House house = new House.Builder()
  .walls("brick")
  .roof("tiles")
  .build();
```

## Advantages
- Handles complex objects with many optional parameters cleanly
- Fluent interface makes code more readable
- Ensures objects are fully initialized before use
- Allows immutable objects (thread-safe)
- Separates construction logic from business logic
- Easy to add new optional properties without breaking existing code
- Validation can happen once in build() method

## Disadvantages
- More code boilerplate (though IDEs help)
- Overkill for simple objects with few parameters
- Requires creating a separate builder class
- Slight performance overhead compared to direct construction

## When NOT to Use
- Objects with only one or two parameters
- Objects that don't need validation
- Simple data transfer objects (DTOs)
- When immutability isn't important

## Common Mistakes
- Making the builder mutable (reusable builders)
- Not providing default values for optional parameters
- Forgetting validation in build() method
- Using builder for simple objects (over-engineering)
- Not making the final product immutable

## Framework / Library Usage
- **Java**: StringBuilder, Lombok (@Builder), Java 14+ records
- **Guava**: ImmutableList.Builder, ImmutableMap.Builder
- **Spring**: ServerRequestBuilder, RestTemplate
- **SQL**: PreparedStatement uses builder pattern
- **Protocol Buffers**: Auto-generated builders for message types

## System Design Use Cases
- Configuration objects with many optional settings
- HTTP requests (URL, headers, body, cookies)
- Database connection strings and pool configuration
- JSON/XML document construction
- UI component creation (Android, Swing)
- SQL query builders

## Interview One-Liner
Builder pattern provides a fluent, step-by-step interface for constructing complex objects while maintaining immutability and ensuring valid state.

## Common Interview Questions

### Q1: What problem does Builder solve that constructor overloads don't?

**Answer:** The telescoping constructor anti-pattern requires many overloaded constructors for different parameter combinations:
- Constructor explosion: With 10 optional parameters, you need 2^10 = 1024 overloads
- Order matters: Easy to pass parameters in wrong order (all Strings confuse positions)
- Invalid states: Setters can leave objects partially initialized

Builder solves this by:
- Single builder class with fluent methods (setX().setY().setZ())
- Clear parameter names make code self-documenting
- Validation happens only in build() - prevents invalid states
- Immutable final object once built

**Code Example:**
```java
// Without Builder (hard to read, many overloads)
House h = new House("brick", "tiles", "glass", "wood", true, false);

// With Builder (clear intent)
House h = new HouseBuilder()
    .withWalls("brick")
    .withRoof("tiles")
    .withWindows("glass")
    .withDoors("wood")
    .hasGarden(true)
    .build();
```

### Q2: How does Builder relate to immutability?

**Answer:** Builder enables immutability by:
1. Separating construction (mutable builder) from usage (immutable object)
2. Final object is created all at once in build() - no setters after creation
3. Builder handles complex initialization - final object just stores values
4. Thread-safe: Immutable objects can be shared safely

**Code Example:**
```java
public class Student {
    private final String name;
    private final int age;
    private final List<String> courses;
    
    private Student(StudentBuilder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.courses = Collections.unmodifiableList(builder.courses);
    }
    
    public static class StudentBuilder {
        // ... fluent methods return 'this' ...
        
        public Student build() {
            return new Student(this); // Create immutable instance
        }
    }
}
```

### Q3: What problems arise without Builder Pattern?

**Answer:** Without Builder:
- **Consistency issues**: Objects can be left in incomplete states with missing required fields
- **Constructor hell**: Adding optional parameters requires recompiling and updating all constructors
- **Hard to maintain**: Changes to object structure break many constructor signatures
- **Readability**: `new House("brick", "tiles", null, null, true)` - unclear what parameters mean
- **Thread-safety**: Setters after construction can cause race conditions
- **Validation burden**: Must validate in every constructor overload

### Q4: How is Builder different from Factory?

**Answer:** 
| Aspect | Builder | Factory |
|--------|---------|---------|
| **Purpose** | Construct complex objects step-by-step | Create objects (right type) |
| **Complexity** | For objects with many optional parameters | For choosing which concrete type to create |
| **Control** | User controls each step | Factory hides creation logic |
| **Immutability** | Final object is immutable | Creates mutable objects |
| **Use Case** | Complex object with options (House, SQL Query) | Choosing implementation (ShapeFactory) |

**Example:**
```java
// Factory - decides WHICH object type
Shape s = ShapeFactory.getShape("circle"); // Circle or Rectangle?

// Builder - HOW to construct complex object
House h = new HouseBuilder()
    .withWalls("brick")
    .withRoof("tiles")
    .build();
```

### Q5: Can you make a reusable builder? Should you?

**Answer:** Yes, reusable builders can be designed:

```java
// Reusable builder for multiple objects
public class GenericBuilder<T> {
    private final Supplier<T> supplier;
    private final List<Consumer<T>> functions = new ArrayList<>();
    
    public GenericBuilder(Supplier<T> supplier) {
        this.supplier = supplier;
    }
    
    public <V> GenericBuilder<T> with(BiConsumer<T, V> setter, V value) {
        functions.add(obj -> setter.accept(obj, value));
        return this;
    }
    
    public T build() {
        T obj = supplier.get();
        functions.forEach(f -> f.accept(obj));
        return obj;
    }
}

// Usage
Person p = new GenericBuilder<>(Person::new)
    .with(Person::setName, "John")
    .with(Person::setAge, 30)
    .build();
```

**Should you?** It depends:
- ✓ For simple objects with setters
- ✗ For complex objects needing validation (use specific builders)
- ✗ If you need immutability (generic builder requires setters)
- Use Lombok `@Builder` annotation in practice instead

### Q6: What's the performance impact of using Builder?

**Answer:** 
- **Memory**: +1 temporary builder object (negligible for most cases)
- **Speed**: Extra method calls during building (negligible - compiler inlines)
- **GC**: Builder object collected after build() (no issue)

**When it matters:**
- Not a performance bottleneck in most applications
- Creating millions of objects in tight loops could show difference (rare)
- Trade-off for code clarity is usually worth it

**Example:**
```java
// Same performance (compiler optimizes)
House h = new HouseBuilder().withWalls("brick").build();
House h2 = new House("brick"); // Not much difference in bytecode
```

### Q7: How would you combine Builder with other patterns?

**Answer:**
```java
// Builder + Composite (building trees)
TreeNode root = new NodeBuilder()
    .addChild(new NodeBuilder().withValue(1).build())
    .addChild(new NodeBuilder().withValue(2).build())
    .build();

// Builder + Strategy (configuring strategy at build time)
Request req = new RequestBuilder()
    .withRetryStrategy(new ExponentialBackoffStrategy())
    .withTimeout(5000)
    .build();

// Builder + Template Method (builder with hooks)
public abstract class SpecializedBuilder<T> {
    protected T build() {
        validateFields();      // Template method hook
        T obj = construct();
        postConstruct(obj);    // Another hook
        return obj;
    }
}
```

