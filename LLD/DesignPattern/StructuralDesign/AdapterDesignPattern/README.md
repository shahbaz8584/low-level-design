# Adapter Pattern

## What is it?
The Adapter pattern converts the interface of a class into another interface clients expect. It allows incompatible objects to collaborate.

## When to use it?
- Have incompatible interfaces that need to work together
- Integrate third-party libraries with different interfaces
- Want to reuse existing class with incompatible interface
- Create bridge between old and new code

## Real-world Example
**Weight Machine Adapter**: Weight machine returns weight in different formats. Adapter converts to pounds/kg format that clients expect.

## Key Benefits
✓ Allows collaboration between incompatible objects
✓ Improves code reusability
✓ Follows Single Responsibility Principle
✓ Follows Open/Closed Principle
✓ Helps integrate legacy code

## Key Drawbacks
✗ Added complexity with extra adapter class
✗ Can hide design problems in original interfaces
✗ May reduce performance due to indirection
✗ Overkill for simple interface changes

## Easy Analogy
**Think of it like an electrical adapter:**
You have a device (WeightMachine) that outputs in kg, but your client needs it in pounds. The adapter converts kg to pounds without changing the original device.

## Implementation Notes
- **Target**: Interface clients expect
- **Adaptee**: Existing interface that needs adaptation
- **Adapter**: Converts adaptee interface to target interface
- **Two types**: Class adapter (inheritance) and object adapter (composition)
- Object adapter is preferred (more flexible)
- Keep adapter simple (avoid complex logic)
- Consider whether modification of adaptee is possible
