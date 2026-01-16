# Strategy Pattern

## What is it?
The Strategy pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable. It lets the algorithm vary independently from clients that use it.

## When to use it?
- Many related classes differ only in behavior
- You need different variants of an algorithm
- Avoid conditional statements for algorithm selection
- Need to switch algorithms at runtime

## Real-world Example
**Vehicle Driving Modes**: Normal vehicle drives normally, sports vehicle can switch to sports drive mode. Different driving strategies without changing vehicle class.

## Key Benefits
✓ Eliminates conditional statements
✓ Easy to switch algorithms at runtime
✓ Single Responsibility: Each strategy handles one algorithm
✓ Open/Closed Principle: Add new strategies easily
✓ Improved testability (test each strategy independently)

## Key Drawbacks
✗ Creates many strategy classes (increases classes count)
✗ Overkill for simple algorithms
✗ Client must know about strategies
✗ Runtime overhead from dynamic dispatch

## Easy Analogy
**Think of it like choosing different routes to reach your destination:**
You have a car (Vehicle) → You can use GoogleMaps route (Strategy1) → Or Waze route (Strategy2) → Or local knowledge route (Strategy3). Same destination, different strategies. You can switch anytime.

## Implementation Notes
- **Context**: Uses strategy interface, doesn't know concrete strategy
- **Strategy**: Interface defining algorithm contract
- **ConcreteStrategy**: Implements specific algorithm
- Strategy can be set during initialization or at runtime
- Use dependency injection for strategy assignment
- Strategies should be stateless when possible
