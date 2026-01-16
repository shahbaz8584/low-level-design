# Prototype Pattern

## What is it?
The Prototype pattern creates new objects by copying an existing object (prototype) rather than creating from scratch. Useful when object creation is expensive.

## When to use it?
- Object creation is expensive or slow
- Need to avoid subclassing for object creation
- Want to create clones of objects with different states
- Need to decouple object creation from concrete classes

## Real-world Example
**Network Connection Cloning**: Clone existing network connections (shallow/deep cloning) with different configurations instead of creating from scratch.

## Key Benefits
✓ Faster object creation (copy existing object)
✓ Avoids expensive initialization
✓ Reduces memory usage in some cases
✓ Decouples client from concrete classes
✓ Flexible cloning strategies

## Key Drawbacks
✗ Shallow vs deep cloning complexity
✗ Clone method implementation required for all classes
✗ Circular references complicate deep cloning
✗ Performance overhead from copying large objects

## Easy Analogy
**Think of it like photocopying documents:**
You have an original document → You make a photocopy (shallow clone) → But if the original has attachments, the copy references the same attachments. With deep copy, you also copy the attachments.

## Implementation Notes
- Implement Cloneable interface
- Override clone() method to create copy
- **Shallow Clone**: Only copy reference fields (uses default clone)
- **Deep Clone**: Create new instances of referenced objects
- Use copy constructor alternative to clone()
- Be careful with mutable fields (may need deep clone)
- Handle CloneNotSupportedException
