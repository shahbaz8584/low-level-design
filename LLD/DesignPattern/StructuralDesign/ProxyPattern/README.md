# Proxy Pattern

## What is it?
The Proxy pattern provides a surrogate or placeholder for another object to control access to it. Proxy acts on behalf of the real object.

## When to use it?
- Defer expensive object creation (lazy initialization)
- Control access to sensitive objects
- Log access to objects
- Cache results of expensive operations
- Restrict operations on objects

## Real-world Example
**Employee Data Cache Proxy**: Real expensive employee data fetches from DB. Proxy caches data in Redis, returning cached results when available.

## Key Benefits
✓ Control access to objects
✓ Lazy initialization (create object only when needed)
✓ Add logging/caching without modifying original
✓ Follows Single Responsibility Principle
✓ Transparent to client code

## Key Drawbacks
✗ Added complexity with extra proxy class
✗ Performance overhead from proxy indirection
✗ Response time slightly increased
✗ Overkill for simple objects

## Easy Analogy
**Think of it like a personal assistant:**
You (Real Subject) are busy. Your assistant (Proxy) handles your calls → If someone asks your salary, assistant checks notes (cache) → If not there, asks you. No one directly bothers you.

## Implementation Notes
- **Subject**: Interface for proxy and real object
- **RealSubject**: Actual object being proxied
- **Proxy**: Manages access to real subject
- Proxy implements same interface as subject
- Can control access, cache, or defer creation
- **Types**: Protection proxy, virtual proxy, cache proxy, remote proxy
- Maintain reference to real subject
- Delegate calls to real subject after proxy logic
