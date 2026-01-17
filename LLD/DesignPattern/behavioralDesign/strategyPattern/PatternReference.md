# Strategy Pattern — Interview Reference

## Intent
Define a family of algorithms, encapsulate each one, and make them interchangeable.

## Problem Statement
Multiple algorithms are implemented with conditionals in client code making it hard to extend or test.

## Why Simple Code Fails
Conditionals couple clients to algorithm implementations; adding new strategies requires modifying client code.

## Solution Overview
Extract algorithms into Strategy interfaces and inject appropriate Strategy into the Context.

## UML Diagram
See `UML/ClassDiagram.md` and generated diagram at `build/diagrams/behavioralDesign_strategyPattern_UML_ClassDiagram.md.png` (if present).

## Participants / Roles
- Strategy (interface)
- ConcreteStrategy (implementations)
- Context (uses Strategy)

## Runtime Execution Flow
1. Client selects or injects a Strategy into Context
2. Context delegates algorithm calls to Strategy
3. Strategy executes algorithm and returns result

## Minimal Java Example
## Without Pattern

```java
// Without Strategy: conditional selection of algorithm
if(mode=="zip") compressZip(data); else compressGzip(data);
```

## With Pattern

```java
// With Strategy: inject Compression strategy
Compressor c = new Compressor(new ZipCompression());
c.compress(data);
```


```java
public interface Compression { byte[] compress(byte[] data); }
public class ZipCompression implements Compression { public byte[] compress(byte[] d){/*...*/} }
public class Compressor {
  private Compression compression;
  public Compressor(Compression c){ this.compression = c; }
  public void compress(byte[] data){ compression.compress(data); }
}
```

## Advantages
- Algorithms are isolated and interchangeable
- Follows Open/Closed: add new strategies without changing clients

## Disadvantages
- More classes/code
- Clients must be aware of Strategy selection

## When NOT to Use
- If only one algorithm exists and unlikely to change

## Common Mistakes
- Exposing too many strategy methods
- Putting selection logic back into Context

## Framework / Library Usage
- Use DI frameworks (Spring) to wire strategies as beans and select by qualifier or profile

## System Design Use Cases
- Sorting strategies, compression formats, payment routing strategies, retry/backoff policies

## Interview One-Liner
Strategy encapsulates an interchangeable algorithm so clients can select behavior at runtime.

## Common Interview Questions
- How to combine Strategy with Factory for selection?
- How to avoid strategy explosion?
