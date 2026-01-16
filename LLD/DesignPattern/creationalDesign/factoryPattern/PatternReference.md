# Factory Pattern — Interview Reference

## Intent
Create objects without exposing instantiation logic; return interface types to callers.

## Problem Statement
Client code depends on concrete classes; changes to construction scatter across codebase.

## Why Simple Code Fails
Using `new` and `if-else` spreads knowledge of implementations, breaking Open/Closed and making tests harder.

## Solution Overview
Introduce a Factory that encapsulates object creation behind a method returning a product interface.

## UML Diagram
See [UML/ClassDiagram.md](UML/ClassDiagram.md) and generated diagram at `build/diagrams/creationalDesign_factoryPattern_UML_ClassDiagram.md.png`.

## Participants / Roles
- Product (interface)
- ConcreteProduct (implementations)
- Creator/Factory (declares factory method)
- ConcreteCreator (constructs ConcreteProduct)

## Runtime Execution Flow
1. Client calls Factory.get(...)
2. Factory decides which ConcreteProduct to instantiate
3. Factory returns Product interface to client

## Minimal Java Example
```java
public interface Shape { void draw(); }
public class Circle implements Shape { public void draw(){ /*...*/ } }
public class ShapeFactory {
  public static Shape getShape(String type){
    if("circle".equals(type)) return new Circle();
    return null;
  }
}
```

## Advantages
- Decouples clients from concrete classes
- Centralizes creation logic
- Easier to add new product types

## Disadvantages
- Extra indirection and classes
- Can be overused for trivial construction

## When NOT to Use
When construction is simple and unlikely to change.

## Common Mistakes
- Putting business logic in factories
- Returning concrete types instead of interfaces

## Framework / Library Usage
- Spring: use `FactoryBean` or configuration classes
- Java: `Supplier<T>`, enums, or dependency injection containers

## System Design Use Cases
- Plugin loaders, parser factories, message handler factories, UI component creation

## Interview One-Liner
Factory encapsulates object creation and returns abstractions to decouple clients.

## Common Interview Questions
- How to avoid switch/if-else in factories?
- When choose Factory vs Builder?
