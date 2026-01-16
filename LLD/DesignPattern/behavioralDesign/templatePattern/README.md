# Template Pattern

## What is it?
The Template Pattern defines the skeleton of an algorithm in a method, deferring some steps to subclasses. It lets subclasses redefine certain steps without changing the algorithm's structure.

## When to use it?
- Multiple classes have similar algorithm structure
- Want to avoid code duplication
- Define invariant parts in base class, variant parts in subclasses
- Invert control (Hollywood principle: "Don't call us, we'll call you")

## Real-world Example
**Payment Processing**: PaymentFlow template defines steps (validate → charge → confirm). Different payment types (ToFriend, ToMerchant) implement specific steps differently.

## Key Benefits
✓ Eliminates code duplication
✓ Single Responsibility: Separate algorithm structure from implementation
✓ Open/Closed Principle: Extend without modifying template
✓ Consistent algorithm execution
✓ Easy to maintain and extend

## Key Drawbacks
✗ Class hierarchy may become complex
✗ Violation of Liskov Substitution if subclasses don't follow contract
✗ Tight coupling between base and derived classes
✗ Limited flexibility in algorithm structure

## Easy Analogy
**Think of it like a cooking recipe:**
All recipes have basic steps: Prepare ingredients → Cook → Plate up. But within each step, the details differ. Pizza preparation is different from Biryani preparation. The template (steps) stays the same, details vary.

## Implementation Notes
- **AbstractClass**: Defines template method with algorithm skeleton
- Template method calls abstract methods (hooks)
- Subclasses override specific abstract methods
- Use final keyword on template method to prevent override
- Consider using hooks (extension points) for optional customization
- Keep base class focused on algorithm structure
