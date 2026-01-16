# Singleton Pattern

## What is it?
The Singleton pattern restricts the instantiation of a class to a single object and provides a global point of access to that instance.

## When to use it?
- Only one instance of a class should exist (e.g., database connection, logger)
- Need global access to the instance
- Ensure instance is created only once
- Control access to shared resource

## Real-world Example
**Database Connection**: Only one DB connection instance should exist for the entire application. Singleton ensures single instance and global access.

## Key Benefits
✓ Ensures single instance
✓ Global point of access
✓ Lazy initialization possible
✓ Thread-safe implementation available
✓ Prevents multiple instantiation

## Key Drawbacks
✗ Difficult to test (global state)
✗ Hidden dependencies (singleton dependency not clear)
✗ Violates Single Responsibility Principle
✗ Not thread-safe in all implementations
✗ Makes code less flexible

## Easy Analogy
**Think of it like a country's president:**
There's only ONE president at a time. When you need to talk to the president, you ask for "the president" not "create a new president". Everyone gets the same president instance.

## Implementation Notes
- Private constructor to prevent instantiation
- Static instance variable
- Public static getInstance() method
- **Eager Initialization**: Create instance at class loading
- **Lazy Initialization**: Create instance on first access
- **Thread-safe**: Use synchronized block or double-checked locking
- Consider using Enums for thread-safe singleton
- Implement Serializable/Cloneable carefully to maintain singleton
