# Composite Pattern

## What is it?
The Composite pattern composes objects into tree structures to represent part-whole hierarchies. It allows clients to treat individual objects and compositions uniformly.

## When to use it?
- Represent hierarchical structures (trees)
- Clients should treat individual and composite objects the same way
- Part-whole relationships need to be represented
- File system or menu structures

## Real-world Example
**File System**: Files and directories form a tree. Both can have operations like get size, copy. Composite pattern treats them uniformly.

## Key Benefits
✓ Simplifies client code (same treatment for leaf and composite)
✓ Flexible tree structures (easy to add/remove nodes)
✓ Follows Single Responsibility Principle
✓ Follows Open/Closed Principle
✓ Recursive composition is natural

## Key Drawbacks
✗ May force inappropriate operations on leaves
✗ Less type safety (both leaf and composite look same)
✗ Complexity increases with tree depth
✗ Performance issues with deep trees

## Easy Analogy
**Think of it like a folder structure on your computer:**
Folders contain files and other folders → You can ask any folder/file "what's your size?" → A folder calculates size by adding all children's sizes. Same operation, different implementations.

## Implementation Notes
- **Component**: Declares common operations
- **Leaf**: Represents leaf objects (no children)
- **Composite**: Represents composite objects (has children)
- Composite implements add/remove/get children methods
- Both leaf and composite implement component operations
- Watch out for inappropriate operations on leaves
- Consider empty checks for operations on leaves
