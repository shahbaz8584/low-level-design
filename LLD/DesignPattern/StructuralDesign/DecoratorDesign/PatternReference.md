# Decorator Pattern — Interview Reference

## Intent
Attach additional responsibilities to an object dynamically, providing a flexible alternative to subclassing for extending functionality.

## Problem Statement
When you need to add features to objects, subclassing creates inflexible hierarchies. A Coffee class with addMilk(), addSugar(), addChocolate() variants requires exponential classes (CoffeeWithMilk, CoffeeWithMilkAndSugar, etc.). Decorator allows combining features dynamically without inheritance explosion.

## Why Simple Code Fails
Using inheritance for optional features creates:
- Exponential class explosion (2^n combinations for n features)
- Tight coupling between features
- Difficult to add/remove features at runtime
- Violates Single Responsibility (classes do too much)

## Solution Overview
Define a Component interface. Create concrete components and decorators that wrap components while implementing the same interface. Decorators add behavior before/after delegating to the wrapped object.

## Participants / Roles
- Component (interface for objects that can be decorated)
- ConcreteComponent (base object to be decorated)
- Decorator (abstract wrapper implementing Component)
- ConcreteDecorator (adds specific responsibilities)

## Runtime Execution Flow
1. Client wraps ConcreteComponent with Decorators
2. Each decorator wraps the previous one
3. At runtime, client calls the outermost decorator
4. Decorators chain calls down to ConcreteComponent
5. Component executes, results bubble back up with decorations

## Minimal Java Example
## Without Pattern

```java
// Without Decorator: exponential classes for combinations
abstract class Coffee { abstract double cost(); }
class Espresso extends Coffee { public double cost() { return 2.0; } }
class EspressoWithMilk extends Coffee { public double cost() { return 2.0 + 0.5; } }
class EspressoWithMilkAndSugar extends Coffee { public double cost() { return 2.0 + 0.5 + 0.3; } }
class EspressoWithMilkAndSugarAndChocolate extends Coffee { /*...*/ }
// Add more combinations... problem scales exponentially!
```

## With Pattern

```java
// With Decorator: flexible composition of features
public interface Coffee { 
  double cost(); 
  String description();
}

public class Espresso implements Coffee {
  public double cost() { return 2.0; }
  public String description() { return "Espresso"; }
}

public abstract class CoffeeDecorator implements Coffee {
  protected Coffee coffee;
  public CoffeeDecorator(Coffee coffee) { this.coffee = coffee; }
  public double cost() { return coffee.cost(); }  // delegates
  public String description() { return coffee.description(); }  // delegates
}

public class MilkDecorator extends CoffeeDecorator {
  public MilkDecorator(Coffee coffee) { super(coffee); }
  public double cost() { return super.cost() + 0.5; }
  public String description() { return super.description() + ", Milk"; }
}

public class SugarDecorator extends CoffeeDecorator {
  public SugarDecorator(Coffee coffee) { super(coffee); }
  public double cost() { return super.cost() + 0.3; }
  public String description() { return super.description() + ", Sugar"; }
}

public class ChocolateDecorator extends CoffeeDecorator {
  public ChocolateDecorator(Coffee coffee) { super(coffee); }
  public double cost() { return super.cost() + 0.7; }
  public String description() { return super.description() + ", Chocolate"; }
}

// Usage: Combine any features dynamically!
Coffee coffee = new Espresso();  // cost: 2.0, desc: "Espresso"
coffee = new MilkDecorator(coffee);  // cost: 2.5, desc: "Espresso, Milk"
coffee = new SugarDecorator(coffee);  // cost: 2.8, desc: "Espresso, Milk, Sugar"
coffee = new ChocolateDecorator(coffee);  // cost: 3.5, desc: "Espresso, Milk, Sugar, Chocolate"

// Or chain directly
Coffee deluxe = new ChocolateDecorator(
  new SugarDecorator(
    new MilkDecorator(
      new Espresso()
    )
  )
);
```

## Real-World Examples

### Java I/O Streams
```java
// File streaming with decorators
InputStream input = new FileInputStream("data.txt");  // base
input = new BufferedInputStream(input);  // decorator 1: buffering
input = new GZIPInputStream(input);      // decorator 2: compression
// Read through decorated chain
byte[] data = input.read();
```

### UI Components
```java
// Window with optional decorators
Window window = new BasicWindow("Title");
window = new ScrollBarDecorator(window);  // add scroll
window = new FrameDecorator(window);      // add frame
window = new TitleBarDecorator(window);   // add title
window.draw();
```

### Spring HttpHeaders
```java
// Request with headers (each header is a decorator)
HttpHeaders headers = new HttpHeaders();
headers.add("Content-Type", "application/json");
headers.add("Authorization", "Bearer token");
headers.add("Cache-Control", "no-cache");
// Headers decorate the base HTTP request
```

## Advantages
- ✓ **Flexible feature composition** - Combine features dynamically without classes explosion
- ✓ **Single Responsibility** - Each decorator adds one feature
- ✓ **Open/Closed Principle** - Extend without modifying existing classes
- ✓ **Runtime flexibility** - Add/remove features at runtime
- ✓ **Alternative to inheritance** - Avoids deep inheritance hierarchies
- ✓ **Composition over inheritance** - More flexible and maintainable

## Disadvantages
- ✗ **More objects** - Creating decorators creates many small objects in memory
- ✗ **Wrapper overhead** - Each decorator adds a function call layer
- ✗ **Complex debugging** - Deep decorator chains hard to trace
- ✗ **Order matters** - Decorator order can affect behavior
- ✗ **Type checking issues** - instanceof checks difficult with decorated objects

## When NOT to Use
- When only a few feature combinations exist (simple inheritance is better)
- When performance is critical and wrapper overhead matters
- When you need to modify existing behavior (use Strategy instead)
- When decorator order shouldn't matter (mix-in composition or traits might be better)

## Common Mistakes
- Not delegating to wrapped object (breaking the chain)
- Creating decorators for different responsibilities (each should do one thing)
- Using when Strategy or State would be cleaner
- Decorating decorators excessively (becomes unmaintainable)

## Framework / Library Usage
- **Java I/O** - BufferedInputStream, GZIPInputStream, ObjectInputStream
- **Collections** - Collections.unmodifiableList(), Collections.synchronizedList()
- **Servlet API** - HttpServletRequestWrapper, HttpServletResponseWrapper
- **Spring** - SecurityContextHolderStrategyProvider for request decoration

## System Design Use Cases
- **Caching layers** - Decorate service calls with caching decorator
- **Logging/Monitoring** - Decorate methods with logging decorators
- **Permission checking** - Decorate operations with authentication decorators
- **Compression/Encryption** - Decorate streams with compression/encryption

## Interview One-Liner
Decorator wraps objects to add responsibilities dynamically without modifying the original class or using inheritance.

## Common Interview Questions
- How is Decorator different from inheritance?
- Can you decorate a decorator?
- What's the difference between Decorator and Strategy?
- Real-world example of Decorator you've used?

## Interview One-Liner
One-line summary of the pattern.

