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
// Problem: Multiple incompatible interfaces scattered throughout code
LegacyDatabase legacyDB = new LegacyDatabase();
legacyDB.openConnection("host", "port");  // legacy method
legacyDB.executeQuery("SELECT ...");       // legacy method
legacyDB.closeConnection();                // legacy method

// Client code tightly coupled to legacy interface
// Hard to switch to modern database without refactoring everywhere
public void loadUserData(String userId) {
  LegacyDatabase db = new LegacyDatabase();
  db.openConnection("localhost", "3306");
  String result = db.executeQuery("SELECT * FROM users WHERE id=" + userId);
  // Parse result manually
  db.closeConnection();
}
```

## With Pattern

```java
// Solution: Adapter translates legacy interface to modern one
public interface ModernDatabase {
  void connect(String connectionString);
  String query(String sql);
  void disconnect();
}

public class DatabaseAdapter implements ModernDatabase {
  private LegacyDatabase legacyDB = new LegacyDatabase();
  
  @Override
  public void connect(String connectionString) {
    // Translate: parse connection string and call legacy methods
    String[] parts = connectionString.split(":");
    legacyDB.openConnection(parts[0], parts[1]);
  }
  
  @Override
  public String query(String sql) {
    // Translate: legacy returns raw string, modern returns parsed data
    return legacyDB.executeQuery(sql);
  }
  
  @Override
  public void disconnect() {
    legacyDB.closeConnection();
  }
}

// Usage: Client code works with modern interface
public void loadUserData(String userId, ModernDatabase db) {
  db.connect("localhost:3306");
  String result = db.query("SELECT * FROM users WHERE id=" + userId);
  // Work with clean result
  db.disconnect();
}

// Switch between implementations easily
ModernDatabase db = new DatabaseAdapter(); // Uses legacy backend
loadUserData("123", db);
```

## Real-World Examples

### JDBC Adapter Example
```java
// Legacy system uses custom connection API
class OracleConnection {
  public void open(String host, int port) { /*...*/ }
  public void exec(String query) { /*...*/ }
}

// JDBC expects standard interface
public interface java.sql.Connection {
  Statement createStatement();
  void close();
}

// JDBC adapter bridges the gap (internal to driver)
// Users see standard Connection interface, driver handles Oracle specifics
```

### Collections.asSet() Example
```java
// Legacy array-based code
String[] items = {"apple", "banana", "cherry"};

// Modern Collections API
List<String> list = Arrays.asList(items);
Set<String> set = new HashSet<>(list);
// Adapter converts array → List → Set transparently
```

### Android RecyclerView Adapter Example
```java
// RecyclerView expects ViewHolder interface
public abstract class RecyclerView.Adapter<VH extends ViewHolder> {
  abstract void onBindViewHolder(VH holder, int position);
}

// Our app data might be in any format (API response, database)
public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
  private List<User> users;
  
  @Override
  public void onBindViewHolder(UserViewHolder holder, int position) {
    // Adapt: translate User object → ViewHolder UI bindings
    User user = users.get(position);
    holder.nameView.setText(user.getName());
    holder.emailView.setText(user.getEmail());
  }
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

### Q1: Adapter vs Facade — differences?

- **Intent**: Adapter changes an existing interface so it matches what a specific client expects. Facade keeps existing interfaces as‑is but exposes a **simpler, unified API** over a complex subsystem.
- **Scope**: Adapter typically works at the **class/object level** (one adaptee to one target). Facade works at the **subsystem level**, grouping many classes behind one entry point.
- **Direction of use**: With Adapter, **client depends on Target**, and Adapter depends on Adaptee. With Facade, **client depends on Facade**, and the Facade depends on many subsystem classes.
- **When to use**: Use Adapter when you **cannot change** the existing class (third‑party/legacy) but must match a new interface. Use Facade when you want to **hide complexity** and make a subsystem easier to use.

In interviews, mention that both can appear together: a Facade may internally use multiple Adapters to normalize different backends.

### Q2: When to prefer object adapter over class adapter?

- **Languages with single inheritance (like Java)**: Object adapter (composition) is preferred because class adapter (multiple inheritance) is either impossible or very restricted.
- **Flexibility**: Object adapter can wrap **any implementation of the adaptee interface** (or even switch the wrapped object at runtime). Class adapter is tied to **one concrete adaptee class**.
- **Reusability**: With object adapter, one adapter can work with **adaptee subclasses** without modification. Class adapter may need separate subclasses per adaptee variant.
- **Encapsulation**: Object adapter hides the adaptee behind composition, which makes it easier to enforce invariants, caching, logging, etc.

Class adapter is mainly discussed for theory (when multiple inheritance is available); in real Java system design answers, emphasize **object adapter using composition** as the default choice.
