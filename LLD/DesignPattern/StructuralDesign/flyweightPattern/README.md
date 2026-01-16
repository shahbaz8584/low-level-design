# Flyweight Pattern

## What is it?
The Flyweight pattern uses sharing to support large numbers of fine-grained objects efficiently by sharing common state between multiple objects.

## When to use it?
- Application uses large number of similar objects
- Memory usage is a concern
- Intrinsic state can be shared between objects
- Many objects with same properties

## Real-world Example
**Robot Game**: Game has thousands of robots. Instead of creating separate objects, share intrinsic state (sprite type) and keep extrinsic state (position) separate.

## Key Benefits
✓ Significantly reduces memory usage
✓ Improves application performance
✓ Centralizes management of shared state
✓ Good for large-scale applications
✓ Transparent to client code

## Key Drawbacks
✗ Increased complexity in code
✗ Thread-safety concerns (shared objects)
✗ Performance overhead from object lookup
✗ Intrinsic/extrinsic state separation not always clear

## Easy Analogy
**Think of it like a multiplayer game with 10,000 soldiers:**
All soldiers have the same armor type (shared/intrinsic) but different positions (unique/extrinsic). Instead of creating 10,000 objects, create 1 shared soldier object, reuse it with different positions.

## Implementation Notes
- **Flyweight**: Interface for shared and unique states
- **ConcreteFlyweight**: Stores intrinsic state (shared)
- **FlyweightFactory**: Creates and manages flyweight objects
- **Client**: Maintains extrinsic state
- Separate intrinsic (shared) from extrinsic (unique) state
- Use factory pattern to create/retrieve flyweights
- Ensure flyweight objects are immutable
- Consider thread-safety for shared objects
- Useful when you have thousands of similar objects
