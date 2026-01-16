# Memento Pattern

## What is it?
The Memento pattern captures and stores an object's internal state without exposing it, allowing the object to be restored to that state later.

## When to use it?
- Implement undo/redo functionality
- Save checkpoints of an object's state
- Restore previous state without breaking encapsulation
- Maintain history of object states

## Real-world Example
**Text Editor**: Save snapshots of document at different points so users can undo/redo edits without directly accessing internal editor state.

## Key Benefits
✓ Preserves encapsulation (internal state stays hidden)
✓ Provides undo/redo functionality
✓ Doesn't violate Single Responsibility Principle
✓ Allows state restoration without side effects

## Key Drawbacks
✗ Memory overhead if too many states are stored
✗ Time overhead to create/restore snapshots
✗ More complex implementation than simple state management

## Easy Analogy
**Think of it like Google Docs version history:**
Your document (Originator) saves snapshots at certain points → Each snapshot is a Memento → Google (CareTaker) keeps all versions → You can click on any version to restore it.

## Implementation Notes
- **Originator**: Object whose state we want to save
- **Memento**: Stores snapshot of originator's state
- **CareTaker**: Manages memento objects and restoration logic
- Store mementos in a list for undo/redo operations
