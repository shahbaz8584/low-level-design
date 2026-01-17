# Observer Pattern — Interview Reference

## Intent
Define a one-to-many dependency so when one object changes state, its dependents are notified and updated automatically.

## Problem Statement
Multiple components need to react to state changes; tight coupling makes it hard to add/remove listeners.

## Why Simple Code Fails
Hard-coded callbacks scatter notification logic and duplicate update code across observers.

## Solution Overview
Introduce Subject (observable) that maintains a list of Observers; notify them on state changes.

## UML Diagram
See `observable` folder UML and generated diagram at `build/diagrams/behavioralDesign_ObserverPattern_UML_ClassDiagram.md.png` (if present).

## Participants / Roles
- Subject/Observable: holds state and observers
- Observer: interface for update callback
- ConcreteObserver: implements reaction to changes

## Runtime Execution Flow
1. Observers register with Subject
2. Subject changes state and calls notifyObservers()
3. Each Observer.update() executes handling logic

## Minimal Java Example
## Without Pattern

```java
// Without Observer: manual callback invocations across components
serviceChanged(); loggerUpdate(); cacheUpdate();
```

## With Pattern

```java
// With Observer: register observers to subject
subject.register(new LoggerObserver());
subject.register(new CacheObserver());
subject.notifyAll("UPDATE");
```


```java
public interface Observer { void update(String evt); }
public class EventSource {
  private List<Observer> observers = new ArrayList<>();
  public void register(Observer o){ observers.add(o); }
  public void notifyAll(String evt){ observers.forEach(o->o.update(evt)); }
}
```

## Advantages
- Loose coupling between subject and observers
- Dynamic subscription/unsubscription

## Disadvantages
- Can introduce unexpected update order dependencies
- Possible memory leaks if observers not unsubscribed

## When NOT to Use
- High-frequency updates where push costs are too high; prefer polling or debounced events

## Common Mistakes
- Forgetting to unregister observers
- Heavy work inside update() blocking subject

## Framework / Library Usage
- Use event buses (Guava EventBus), Reactive frameworks (RxJava), or Spring ApplicationEvents for production systems

## System Design Use Cases
- UI event handling, cache invalidation, event-driven microservices

## Interview One-Liner
Observer decouples state changes from reaction logic by subscribing observers to subjects.

## Common Interview Questions
- How to handle slow/unreliable observers?
- How to order notifications or handle failures in observers?
