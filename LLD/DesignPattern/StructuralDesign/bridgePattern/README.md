# Bridge Pattern

## What is it?
The Bridge pattern decouples an abstraction from its implementation so that the two can vary independently. It bridges the gap between abstraction and implementation.

## When to use it?
- Abstraction and implementation should be independent
- Changes in implementation shouldn't affect client code
- Want to avoid permanent binding between abstraction and implementation
- Multiple implementations for same abstraction

## Real-world Example
**Living Things Breathing**: Different living things (Dog, Fish, Tree) have different breathing mechanisms (Lungs, Gills, Photosynthesis). Bridge separates living things from breathing processes.

## Key Benefits
✓ Decouples abstraction from implementation
✓ Implementation can change without affecting clients
✓ Avoid permanent binding between abstraction and implementation
✓ Improves code maintainability
✓ Follows Open/Closed Principle

## Key Drawbacks
✗ More complex design (more classes needed)
✗ Overkill for simple hierarchies
✗ Increased indirection affects performance
✗ Can overcomplicate simple designs

## Easy Analogy
**Think of it like a car and its engine types:**
A car is abstraction (LivingThing) → It can be Sedan, SUV (Dog, Fish) → Engine type is implementation (Breathing) → Can be Petrol, Diesel (Lungs, Gills). You can mix any car with any engine.

## Implementation Notes
- **Abstraction**: Defines high-level interface
- **RefinedAbstraction**: Extends abstraction
- **Implementor**: Defines lower-level interface
- **ConcreteImplementor**: Implements specific behavior
- Abstraction should contain reference to Implementor
- Use composition instead of inheritance
- Separate abstraction hierarchy from implementation hierarchy
