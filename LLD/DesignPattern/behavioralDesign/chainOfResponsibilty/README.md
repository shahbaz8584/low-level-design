# Chain of Responsibility Pattern

## What is it?
The Chain of Responsibility pattern allows you to pass requests along a chain of handlers. Each handler decides whether to process the request or pass it to the next handler in the chain.

## When to use it?
- Multiple objects may handle a request, and the handler isn't known in advance
- You want to issue requests without specifying the receiver (e.g., logging at different levels)
- You need to dynamically configure the chain of handlers

## Real-world Example
**Logging System**: Different log levels (Debug → Info → Error) where each level handles its corresponding messages and passes others down the chain.

## Key Benefits
✓ Decouples sender from receiver
✓ Flexible chain configuration at runtime
✓ Single Responsibility: Each handler handles one responsibility
✓ Open/Closed Principle: Add new handlers without modifying existing ones

## Key Drawbacks
✗ Request may not be handled if chain is not properly configured
✗ Difficult to debug (request path not always clear)
✗ Performance overhead from passing through chain

## Easy Analogy
**Think of it like a complaint handling system in a company:**
Your complaint goes to the frontdesk → If they can't solve it, they pass to manager → If manager can't solve, they pass to director. Each person (handler) decides if they can handle it or pass it on.

## Implementation Notes
- Each handler should have a reference to the next handler
- Handler should process request and decide to pass or not
- Create chain before using it
