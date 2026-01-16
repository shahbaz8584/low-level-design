# Adapter Pattern — Interview Reference

## Intent
Convert the interface of a class into another interface clients expect, allowing incompatible interfaces to work together.

## Problem Statement
Existing classes provide useful behavior but expose an incompatible interface required by clients.

## Why Simple Code Fails
Copying or modifying existing classes to fit new interfaces introduces duplication and brittle changes.

## Solution Overview
Provide an Adapter that wraps the adaptee and implements the target interface, translating calls.

## UML Diagram
See `UML/ClassDiagram.md` and generated diagram at `build/diagrams/StructuralDesign_AdapterDesignPattern_UML_ClassDiagram.md.png` (if present).

## Participants / Roles
- Target: desired interface
- Adaptee: existing class with incompatible interface
- Adapter: implements Target and delegates to Adaptee

## Runtime Execution Flow
1. Client uses Target interface
2. Adapter implements Target and translates calls to Adaptee
3. Adaptee performs the work; Adapter returns results to client

## Minimal Java Example
## Without Pattern

```java
// Example not provided.
```

## With Pattern

```java
// Example not provided.
```


```java
public interface MediaPlayer { void play(String file); }
public class LegacyPlayer { public void start(String f){ /*...*/ } }
public class PlayerAdapter implements MediaPlayer {
  private LegacyPlayer adaptee;
  public PlayerAdapter(LegacyPlayer l){ this.adaptee = l; }
  public void play(String file){ adaptee.start(file); }
}
```

## Advantages
- Reuse existing code without modification
- Keeps client code clean and stable

## Disadvantages
- Can add extra layers and indirection

## When NOT to Use
- When you can change both sides or when interfaces are simple to align

## Common Mistakes
- Over-adapting: creating adapters for trivial interface differences
- Hiding performance or semantic differences behind adapter

## Framework / Library Usage
- Adapters commonly appear as wrappers, facades, or compatibility layers in middleware

## System Design Use Cases
- Integrating legacy systems, API version adapters, driver or protocol translation

## Interview One-Liner
Adapter wraps an incompatible interface to match the client's expected interface without changing original code.

## Common Interview Questions
- Adapter vs Facade — differences?
- When to prefer object adapter over class adapter?
