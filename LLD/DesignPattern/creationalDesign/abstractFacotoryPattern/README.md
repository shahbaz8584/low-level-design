# Abstract Factory Pattern

## What is it?
The Abstract Factory pattern provides an interface for creating families of related or dependent objects without specifying their concrete classes.

## When to use it?
- System needs to work with multiple families of related objects
- You want to provide a library showing only interfaces, not implementation
- Need to enforce creating products from same family
- Consistency required among products

## Real-world Example
**Car Manufacturing**: Different car types (Economy, Luxury) need matching components (Engine, Tyre). Abstract factory ensures economy cars get economy components.

## Key Benefits
✓ Isolates concrete classes from client code
✓ Easy to swap product families
✓ Enforces consistency among related products
✓ Simplifies extending to support new families
✓ Single Responsibility: Separate object creation from usage

## Key Drawbacks
✗ More complex than simple factory (multiple factory classes)
✗ Adding new product type requires modifying all factories
✗ Can be overkill for simple scenarios
✗ Indirect object creation increases complexity

## Easy Analogy
**Think of it like different restaurant chains:**
McDonalds Factory makes burgers, fries, coke → KFC Factory makes chicken, fries, coke. Both have same product categories but different implementations. Customers don't care, they just ask the factory.

## Implementation Notes
- **AbstractFactory**: Declares factory methods for creating products
- **ConcreteFactory**: Implements creation of specific product family
- **AbstractProduct**: Interface for related products
- **ConcreteProduct**: Concrete implementation of products
- Client works with abstract interfaces only
- Use when you have 2+ product families with multiple products each
