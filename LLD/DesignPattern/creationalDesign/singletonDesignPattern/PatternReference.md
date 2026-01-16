# Singleton Pattern — Interview Reference

## Intent
Ensure a class has only one instance and provide a global access point.

## Problem Statement
Multiple parts of a system require a single shared resource/configuration; naive globals lead to uncontrolled instantiation and state issues.

## Why Simple Code Fails
Using public static fields or unguarded lazy init leads to thread-safety bugs and testing difficulties.

## Solution Overview
Expose a single instance through a controlled access method; handle lazy initialization safely and consider dependency injection alternatives.

## UML Diagram
See `UML/ClassDiagram.md` and generated diagram at `build/diagrams/creationalDesign_singletonDesignPattern_UML_ClassDiagram.md.png` (if present).

## Participants / Roles
- Singleton (the single instance holder)

## Runtime Execution Flow
1. Client requests Singleton.getInstance()
2. getInstance() either returns existing instance or initializes it safely
3. Client uses shared instance

## Minimal Java Example
```java
public class Config {
  private static volatile Config instance;
  private Config(){}
  public static Config getInstance(){
    if(instance==null){
      synchronized(Config.class){
        if(instance==null) instance = new Config();
      }
    }
    return instance;
  }
}
```

## Advantages
- Controlled access to single instance
- Useful for shared resources

## Disadvantages
- Global state hampers testability
- Can hide dependencies and increase coupling

## When NOT to Use
- When DI can provide better lifecycle and scoping
- For per-request or short-lived objects

## Common Mistakes
- Not handling thread-safety
- Using Singleton as a catch-all global

## Framework / Library Usage
- Use dependency injection (Spring beans with singleton scope) rather than hand-rolled singletons where possible.

## System Design Use Cases
- Configuration managers, caches, connection pools (careful: pools are better as separate objects), logging facades

## Interview One-Liner
Singleton ensures a single instance and global access, but can introduce global state and testability problems.

## Common Interview Questions
- How to write thread-safe lazy singleton in Java?
- When are singletons harmful?
