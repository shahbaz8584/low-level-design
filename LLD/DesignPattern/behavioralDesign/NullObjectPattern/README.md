# Null Object Pattern

## What is it?
The Null Object pattern provides an object as a surrogate for the lack of an object. Instead of returning null or checking for null, use a special "null" object that does nothing.

## When to use it?
- Avoid null pointer exceptions (NullPointerException)
- Eliminate null checks throughout code
- Provide default behavior for missing objects
- Simplify client code logic

## Real-world Example
**Vehicle Factory**: Return a NullVehicle instead of null when a vehicle type is not found, avoiding null checks in client code.

## Key Benefits
✓ Eliminates null checks
✓ Prevents NullPointerException
✓ Simplifies client code
✓ Provides default behavior transparently
✓ Follows Open/Closed Principle

## Key Drawbacks
✗ Can hide bugs (silent failures)
✗ May mask incorrect behavior
✗ Adds extra classes (null objects)
✗ Requires discipline to implement correctly

## Easy Analogy
**Think of it like a backup player on a sports team:**
When the main player gets injured, instead of having "no player", you put in a backup player who can do basic things but doesn't contribute much. Your team keeps running without checking "do we have a player?"

## Implementation Notes
- Create a NullObject class that implements the same interface
- Override methods to do nothing or return default values
- Return NullObject instead of null from factory methods
- Use composition where null object provides safe defaults
