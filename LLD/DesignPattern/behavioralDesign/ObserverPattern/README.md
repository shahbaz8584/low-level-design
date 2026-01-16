# Observer Pattern

## What is it?
The Observer pattern defines a one-to-many dependency where when one object changes state, all dependent objects are notified automatically and updated.

## When to use it?
- A change to one object requires changing unknown number of other objects
- An object should notify others without assuming who they are
- Model real-world event systems (event listeners, MVC architecture)
- Implement pub-sub systems

## Real-world Example
**E-commerce Stock System**: When product stock changes, all observers (email notifier, SMS notifier, mobile app) are notified automatically.

## Key Benefits
✓ Loose coupling between observers and subject
✓ Dynamic subscription/unsubscription
✓ One-to-many communication
✓ Supports event-driven architecture
✓ Easy to extend with new observers

## Key Drawbacks
✗ Observer notification order is not guaranteed
✗ All observers are notified even if they don't need the update
✗ Memory leaks if observers not unsubscribed properly
✗ Debugging can be difficult (implicit dependencies)

## Easy Analogy
**Think of it like YouTube subscriptions:**
You (Observer) subscribe to a channel (Subject) → When the creator uploads a video (state change), YouTube notifies all subscribers automatically. If you unsubscribe, no more notifications.

## Implementation Notes
- **Subject**: Maintains list of observers, notifies on state change
- **Observer**: Interface with update() method
- **ConcreteObserver**: Implements update() to react to changes
- Use weak references to avoid memory leaks
- Consider thread-safety for concurrent observers
