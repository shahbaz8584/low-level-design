# Builder Pattern

## What is it?
The Builder pattern separates the construction of a complex object from its representation, allowing the same construction process to create different representations.

## When to use it?
- Object has many optional parameters
- Creating object with many constructor parameters (telescoping constructors problem)
- Want step-by-step construction of complex objects
- Object construction is expensive or multi-step

## Real-world Example
**Student Registration**: Build student objects with many optional fields (name, age, branch, courses). Avoid complex constructor signatures.

## Key Benefits
✓ Clear, fluent API (readable code)
✓ Handles many optional parameters elegantly
✓ Immutable objects (once built, cannot change)
✓ Single Responsibility: Separate construction from representation
✓ Flexible construction (different representations possible)

## Key Drawbacks
✗ More classes (builder class needed)
✗ More code required compared to simple constructors
✗ Slightly more memory overhead
✗ Overkill for simple objects

## Easy Analogy
**Think of it like ordering a custom pizza:**
You don't say "give me all toppings" or "nothing". You build it step by step: "Base → Cheese → Tomato → Pepperoni → Done!". Finally you get your customized pizza.

## Implementation Notes
- **Builder**: Nested static class with fluent API
- Use method chaining for readability (return this)
- Implement build() to create final object
- Provide sensible defaults for optional parameters
- Constructor should be private to force builder use
- Consider immutability after building
- Can use lombok @Builder annotation for less boilerplate
