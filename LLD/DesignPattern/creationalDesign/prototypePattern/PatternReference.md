# Prototype Pattern — Interview Reference

## Intent
Specify kinds of objects to create using a prototypical instance, and create new objects by copying this prototype.

## Problem Statement
Creating new objects from scratch is expensive when initialization involves complex logic, database queries, or resource allocation. Additionally, tight coupling to concrete classes makes it hard to change object creation strategies at runtime.

## Why Simple Code Fails
Using direct instantiation scattered throughout code creates tight coupling and makes it hard to:
- Clone complex objects with deep state
- Switch between different implementations at runtime
- Handle expensive object creation efficiently
- Test code that depends on specific object types

## Solution Overview
Define a Prototype interface with a clone() method. Create concrete prototypes that implement cloning. Instead of creating new objects with `new`, clone existing prototypes. This allows creating variations efficiently and decouples clients from concrete classes.

## Participants / Roles
- Prototype (declares cloning interface)
- ConcretePrototype (implements cloning)
- Client (works with prototypes)

## Runtime Execution Flow
1. Client obtains a prototype instance
2. Client calls prototype.clone()
3. Prototype creates and returns a copy of itself
4. Client uses the cloned object

## Minimal Java Example
## Without Pattern

```java
// Direct instantiation - expensive and tightly coupled
public class ExpensiveObject {
  private DatabaseConnection db;
  private ComplexConfig config;
  
  public ExpensiveObject() {
    this.db = new DatabaseConnection("localhost");
    this.config = new ComplexConfig();
    // 10+ expensive initialization steps...
  }
}

// Usage requires full initialization each time
ExpensiveObject obj1 = new ExpensiveObject(); // Slow
ExpensiveObject obj2 = new ExpensiveObject(); // Slow
```

## With Pattern

```java
// Prototype interface
public interface Prototype<T> {
  T clone();
}

// Concrete prototype
public class ExpensiveObject implements Prototype<ExpensiveObject> {
  private DatabaseConnection db;
  private ComplexConfig config;
  
  public ExpensiveObject(String host) {
    this.db = new DatabaseConnection(host);
    this.config = new ComplexConfig();
  }
  
  @Override
  public ExpensiveObject clone() {
    try {
      return (ExpensiveObject) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }
}

// Usage - fast cloning
ExpensiveObject prototype = new ExpensiveObject("localhost");
ExpensiveObject obj1 = prototype.clone(); // Fast
ExpensiveObject obj2 = prototype.clone(); // Fast
```

## Advantages
- Avoids expensive initialization by cloning
- Decouples clients from concrete classes
- Can add new prototypes at runtime without code changes
- Better performance when object creation is costly
- Works well with Factory pattern
- Reduces constructor complexity

## Disadvantages
- Cloning complex objects with circular references is tricky
- Shallow copy vs deep copy confusion and bugs
- Objects with final fields can be difficult to clone
- Not all objects should be cloneable (security concerns)
- Requires implementing Cloneable interface properly

## When NOT to Use
- Simple objects with quick instantiation
- Objects that shouldn't be copied (singletons, stateful services)
- Objects with unique identities or database IDs
- When security implications of cloning aren't understood

## Common Mistakes
- Shallow copying instead of deep copying when needed
- Forgetting to handle mutable fields in clones
- Not implementing deep copy for nested objects
- Not handling CloneNotSupportedException properly
- Cloning objects that shouldn't be cloned

## Framework / Library Usage
- **Java**: Cloneable interface, Object.clone()
- **Python**: copy.copy(), copy.deepcopy()
- **C#**: ICloneable interface
- **Spring**: Can prototype scope for beans
- **Apache Commons**: BeanUtils.cloneBean() for shallow cloning

## System Design Use Cases
- Caching objects to avoid expensive recreation
- Configuration object templates
- Game development (cloning game entities/NPCs)
- Graphics systems (cloning complex scene objects)
- Database query result caching

## Interview One-Liner
Prototype pattern allows efficient object creation by cloning existing prototypes instead of expensive instantiation, reducing coupling and overhead.

## Common Interview Questions

### Q1: What's the difference between shallow copy and deep copy?

**Answer:**
| Aspect | Shallow Copy | Deep Copy |
|--------|-------------|----------|
| **Primitives** | Copied | Copied |
| **References** | Shared (same object) | New instances created |
| **Memory** | Less memory | More memory |
| **Changes** | Modifying nested object affects both | Independent copies |
| **Speed** | Faster | Slower |

**Code Example:**
```java
class Person {
    String name;
    Address address; // Reference type
}

// Shallow copy - address is shared
Person p1 = new Person("John", addr);
Person p2 = p1.shallowClone();
p2.address.city = "NYC"; // Changes p1 too!

// Deep copy - address is cloned
Person p3 = p1.deepClone();
p3.address.city = "NYC"; // p1 unaffected
```

### Q2: When should you use Prototype vs Factory?

**Answer:**
| Aspect | Prototype | Factory |
|--------|-----------|---------|
| **Purpose** | Clone existing objects | Create new objects from scratch |
| **Cost** | When cloning is cheaper than creating | When creation logic is complex |
| **Coupling** | Decouples from concrete classes | Centralizes creation logic |
| **Configuration** | Clone with existing state | Create with default state |
| **Use Case** | Expensive initialization, caching | Type selection, inheritance hierarchy |

**Example:**
```java
// Factory - best for choosing type
NetworkConnection conn = ConnectionFactory.create("TCP"); // Creates fresh

// Prototype - best for cloning configured objects
NetworkConnection original = new NetworkConnection(config);
NetworkConnection clone = original.deepClone(); // Same config
```

### Q3: How do you handle circular references in cloning?

**Answer:** Circular references occur when objects reference each other:

```java
// Naive approach - infinite loop
class Node {
    Node next;
    public Node clone() {
        return new Node(next.clone()); // Infinite loop if next -> this
    }
}

// Solution: Track visited objects
Map<Node, Node> visited = new HashMap<>();

public Node clone(Node node, Map<Node, Node> visited) {
    if (visited.containsKey(node)) {
        return visited.get(node); // Return already-cloned copy
    }
    
    Node copy = new Node(node.value);
    visited.put(node, copy);
    
    if (node.next != null) {
        copy.next = clone(node.next, visited);
    }
    
    return copy;
}
```

### Q4: What are the security implications of Cloneable?

**Answer:** 
- **Access to private fields**: Clone bypasses constructor validation
- **Mutable state exposure**: Clones might share mutable fields (shallow copy)
- **Bypassing immutability**: Can clone immutable objects and modify them
- **Type confusion**: Can clone objects intended not to be cloned

**Secure approach:**
```java
// Don't use Cloneable - use copy constructor
public class SecureClass {
    private final String data;
    
    public SecureClass(SecureClass other) {
        this.data = new String(other.data); // Validate/transform
    }
    
    // Don't override clone() at all
}
```

### Q5: How would you implement prototype with immutable objects?

**Answer:** Immutable objects cannot be cloned in traditional sense - create new instances:

```java
public final class ImmutableUser {
    private final String name;
    private final int age;
    private final List<String> roles;
    
    // Copy constructor for "cloning"
    public ImmutableUser(ImmutableUser other) {
        this.name = other.name;
        this.age = other.age;
        this.roles = new ArrayList<>(other.roles); // Deep copy list
    }
    
    // Builder for modification (create new instance)
    public ImmutableUser withAge(int newAge) {
        return new ImmutableUser(this.name, newAge, this.roles);
    }
}

// Usage
ImmutableUser original = new ImmutableUser("John", 30, roles);
ImmutableUser copy = new ImmutableUser(original); // "Clone"
ImmutableUser modified = original.withAge(31); // New instance
```

### Q6: When should you use Prototype vs New?

**Answer:**
```java
// Use 'new' when:
Shape circle = new Circle(); // Simple, cheap initialization

// Use Prototype when:
DatabaseConnection original = expensive(); // Costly DB connection
DatabaseConnection clone = original.clone(); // Reuse connection pool

// Use Prototype when:
GameEntity template = loadTemplate("Orc");
GameEntity orc1 = template.clone(); // Many identical creatures
GameEntity orc2 = template.clone();
GameEntity orc3 = template.clone();
```

### Q7: How does Prototype relate to Registry Pattern?

**Answer:** Prototype + Registry = Prototype manager:

```java
// Registry of prototypes
public class ShapeRegistry {
    private Map<String, Shape> prototypes = new HashMap<>();
    
    public void registerPrototype(String name, Shape prototype) {
        prototypes.put(name, prototype);
    }
    
    public Shape createShape(String name) {
        return prototypes.get(name).clone(); // Clone from registry
    }
}

// Usage
registry.registerPrototype("redCircle", new Circle(10, "red"));
Shape s1 = registry.createShape("redCircle"); // Cloned red circle
Shape s2 = registry.createShape("redCircle"); // Another red circle

// Benefits:
// - Centralized prototype management
// - Easy to add new shapes without code changes
// - Configurations stored in registry
```

