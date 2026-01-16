# Factory Pattern

## What is it?
The Factory pattern provides an interface for creating objects, but lets subclasses decide which class to instantiate. It creates objects without specifying the exact classes to create.

## When to use it?
- A class can't anticipate the type of objects it needs to create
- Want to delegate object creation to subclasses
- Object creation logic is complex and needs to be centralized
- Need to decouple object creation from usage

## Real-world Example
**Shape Creation**: Factory creates different shapes (Circle, Rectangle) based on input type. Client doesn't need to know concrete shape classes.

## Key Benefits
✓ Decouples client from concrete classes
✓ Centralizes object creation logic
✓ Easy to add new object types
✓ Follows Open/Closed Principle
✓ Simplifies client code

## Key Drawbacks
✗ More classes needed (factory + concrete classes)
✗ Can be overkill for simple object creation
✗ Indirection makes code harder to follow
✗ Over-abstraction in simple scenarios

## Easy Analogy
**Think of it like a car dealership:**
You go to the dealership (Factory) and ask for a "Honda Civic" (type). The dealership gives you the right car without you worrying how it's built. You just know the interface: "it has 4 wheels, can start and stop".

## Implementation Notes
- **Creator/Factory**: Interface with factory method
- **ConcreteCreator**: Implements factory method for specific type
- **Product**: Interface for created objects
- **ConcreteProduct**: Concrete implementation
- Use enums or string identifiers for object type selection
- Can be static method factory instead of inheritance-based
- Ensure all created objects conform to product interface
