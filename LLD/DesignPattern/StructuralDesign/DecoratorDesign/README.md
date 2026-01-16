# Decorator Pattern

## What is it?
The Decorator pattern attaches additional responsibilities to an object dynamically. It provides a flexible alternative to subclassing for extending functionality.

## When to use it?
- Add responsibilities to individual objects without affecting others
- Subclassing would create too many classes
- Need to combine multiple behaviors dynamically
- Avoid "explosion" of subclasses

## Real-world Example
**Ice Cream Shop**: Base ice cream (Vanilla, Chocolate) can be decorated with toppings (ChocolateSyrup, ChocoChips). Each decorator adds features.

## Key Benefits
✓ More flexible than subclassing
✓ Add/remove responsibilities at runtime
✓ Combine behaviors dynamically
✓ Single Responsibility: Each decorator has one responsibility
✓ Avoids subclass explosion

## Key Drawbacks
✗ Many small classes (decorators)
✗ Complex object composition
✗ Decoration order matters (may affect functionality)
✗ Can be overkill for simple additions

## Easy Analogy
**Think of it like customizing a shirt:**
Basic shirt → Add sleeves (decorator) → Add buttons (another decorator) → Add collar (another decorator). Each decorator adds functionality without changing the original shirt.

## Implementation Notes
- **Component**: Interface for decorators and concrete objects
- **ConcreteComponent**: Original object being decorated
- **Decorator**: Abstract class maintaining reference to component
- **ConcreteDecorator**: Adds specific responsibility
- Decorator wraps component and delegates calls
- Can wrap decorator with another decorator
- Maintain same interface as component
- Watch order of decoration (may affect results)
