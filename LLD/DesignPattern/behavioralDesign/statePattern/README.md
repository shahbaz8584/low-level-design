# State Pattern

## What is it?
The State pattern allows an object to alter its behavior when its internal state changes. The object appears to change its class when the state changes.

## When to use it?
- Object behavior depends on state and must change at runtime
- Different behaviors for different states
- State transitions are complex
- Avoid large if-else chains checking object state

## Real-world Example
**ATM Machine**: Behavior changes based on states (IdleState → CardInsertedState → PinVerifiedState). Operations allowed depend on current state.

## Key Benefits
✓ Eliminates large conditional statements
✓ Single Responsibility: Each state class handles one state
✓ Open/Closed Principle: Add new states without modifying existing ones
✓ Makes state transitions explicit
✓ Improves code readability and maintainability

## Key Drawbacks
✗ Creates many state classes (increases complexity)
✗ Overkill for simple state machines
✗ States need access to context (tight coupling possible)

## Easy Analogy
**Think of it like an ATM machine:**
IdleState → You insert card (CardInsertedState) → You enter PIN (PinVerifiedState) → You withdraw money. At each state, different operations are allowed. You can't withdraw without entering PIN.

## Implementation Notes
- **Context**: Maintains instance of concrete state
- **State**: Interface defining state-specific behavior
- **ConcreteState**: Implements behavior for specific state
- Context delegates calls to current state
- States can change context state via context reference
- Use state pattern for 3+ states or complex transitions
