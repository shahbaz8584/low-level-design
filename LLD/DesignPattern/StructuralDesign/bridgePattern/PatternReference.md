# Bridge Pattern — Interview Reference

## Intent
Decouple an abstraction from its implementation so the two can vary independently.

## Problem Statement
Consider a Shape hierarchy (Circle, Rectangle, Triangle) that needs to support multiple rendering engines (OpenGL, DirectX, Vulkan). Without Bridge, you'd create 3×3=9 classes (CircleOpenGL, CircleDirectX, RectangleOpenGL, etc.). The problem explodes as you add shapes or renderers.

## Why Simple Code Fails
Using inheritance to combine abstractions and implementations:
- Creates exponential class combinations (m shapes × n implementations = m*n classes)
- Tightly couples shape logic to rendering logic
- Hard to add new shapes or renderers without creating new classes
- Violates Single Responsibility (classes do too much)

## Solution Overview
Separate abstraction (what shapes do) from implementation (how they render). Create an abstraction hierarchy and an implementation hierarchy, then bridge them via composition. The abstraction delegates to the implementation.

## Participants / Roles
- Abstraction (Shape interface)
- RefinedAbstraction (Circle, Rectangle)
- Implementor (Renderer interface)
- ConcreteImplementor (OpenGLRenderer, DirectXRenderer)

## Runtime Execution Flow
1. Client creates a Shape with a specific Renderer
2. Shape methods delegate to Renderer implementation
3. Renderer handles platform-specific rendering
4. Shape provides high-level API, Renderer provides low-level details

## Minimal Java Example
## Without Pattern

```java
// Without Bridge: exponential class explosion
// Problem: 3 shapes × 3 renderers = 9 classes!

abstract class Shape {}
class CircleOpenGL extends Shape { public void draw() { /*OpenGL circle*/ } }
class CircleDirectX extends Shape { public void draw() { /*DirectX circle*/ } }
class CircleVulkan extends Shape { public void draw() { /*Vulkan circle*/ } }
class RectangleOpenGL extends Shape { public void draw() { /*OpenGL rect*/ } }
class RectangleDirectX extends Shape { public void draw() { /*DirectX rect*/ } }
class RectangleVulkan extends Shape { public void draw() { /*Vulkan rect*/ } }
// ... add more shapes or renderers, complexity grows!

// Hard to switch renderers at runtime
Shape s = new CircleOpenGL();  // locked into OpenGL
// To use DirectX, need to create new CircleDirectX instance
```

## With Pattern

```java
// With Bridge: 3 shapes + 3 renderers = 6 classes total (NOT 9!)

// Abstraction: what shapes do
public abstract class Shape {
  protected Renderer renderer;  // bridge to implementation
  
  public Shape(Renderer renderer) {
    this.renderer = renderer;
  }
  
  public abstract void draw();
}

// Refined Abstraction: specific shapes
public class Circle extends Shape {
  private double radius;
  
  public Circle(Renderer renderer, double radius) {
    super(renderer);
    this.radius = radius;
  }
  
  @Override
  public void draw() {
    renderer.drawCircle(radius);  // delegates to implementation
  }
}

public class Rectangle extends Shape {
  private double width, height;
  
  public Rectangle(Renderer renderer, double width, double height) {
    super(renderer);
    this.width = width;
    this.height = height;
  }
  
  @Override
  public void draw() {
    renderer.drawRectangle(width, height);  // delegates
  }
}

// Implementor: how rendering happens (abstract interface)
public interface Renderer {
  void drawCircle(double radius);
  void drawRectangle(double width, double height);
}

// Concrete Implementors: platform-specific rendering
public class OpenGLRenderer implements Renderer {
  @Override
  public void drawCircle(double radius) {
    System.out.println("OpenGL: Drawing circle with radius " + radius);
    // OpenGL-specific code...
  }
  
  @Override
  public void drawRectangle(double w, double h) {
    System.out.println("OpenGL: Drawing rectangle " + w + "x" + h);
    // OpenGL-specific code...
  }
}

public class DirectXRenderer implements Renderer {
  @Override
  public void drawCircle(double radius) {
    System.out.println("DirectX: Drawing circle with radius " + radius);
    // DirectX-specific code...
  }
  
  @Override
  public void drawRectangle(double w, double h) {
    System.out.println("DirectX: Drawing rectangle " + w + "x" + h);
    // DirectX-specific code...
  }
}

// Usage: Flexible combinations!
Renderer openGL = new OpenGLRenderer();
Renderer directX = new DirectXRenderer();

Shape circle = new Circle(openGL, 5.0);  // OpenGL circle
circle.draw();  // Uses OpenGL renderer

Shape rect = new Rectangle(directX, 10.0, 20.0);  // DirectX rectangle
rect.draw();  // Uses DirectX renderer

// Easy to switch renderers at runtime!
Renderer currentRenderer = getConfiguredRenderer();  // Could be any renderer
Shape shape = new Circle(currentRenderer, 5.0);
shape.draw();
```

## Real-World Examples

### JDBC Database Bridge
```java
// Abstraction: DataSource interface
public interface DataSource {
  Connection getConnection();
}

// Implementations: Different databases
public class MysqlDataSource implements DataSource { /*...*/ }
public class PostgresDataSource implements DataSource { /*...*/ }
public class OracleDataSource implements DataSource { /*...*/ }

// Usage: Switch databases without code change
String dbType = config.getDatabase();  // "mysql" or "postgres"
DataSource ds = DataSourceFactory.create(dbType);
Connection conn = ds.getConnection();
```

### Logging Framework Bridge
```java
// Abstraction: Logger interface
public interface Logger {
  void debug(String msg);
  void info(String msg);
  void error(String msg);
}

// Implementations: Different logging backends
public class Log4jLogger implements Logger { /*...*/ }
public class SLF4jLogger implements Logger { /*...*/ }
public class JuliLogger implements Logger { /*...*/ }

// Usage: Switch logging framework easily
Logger logger = LoggerFactory.getLogger("com.example");
logger.info("Application started");
```

### Device Driver Bridge
```java
// Abstraction: Device
public abstract class Device {
  protected DeviceImplementation implementation;
  public abstract void operate();
}

// Implementations: Different OS drivers
public interface DeviceImplementation {
  void turnOn();
  void turnOff();
}

public class WindowsDeviceImpl implements DeviceImplementation { /*...*/ }
public class LinuxDeviceImpl implements DeviceImplementation { /*...*/ }
```

## Advantages
- ✓ **Decoupling** - Separates abstraction from implementation
- ✓ **Independent variation** - Change shape logic or rendering independently
- ✓ **Avoids class explosion** - m+n classes instead of m×n
- ✓ **Runtime flexibility** - Switch implementations at runtime
- ✓ **Extensibility** - Add new shapes or renderers without touching existing code
- ✓ **Single Responsibility** - Each class has one reason to change

## Disadvantages
- ✗ **Increased complexity** - More classes and indirection
- ✗ **Overhead** - Delegation adds function call overhead
- ✗ **Learning curve** - Takes time to understand the pattern
- ✗ **Premature design** - Unnecessary if variations are simple

## When NOT to Use
- When you have only one implementation (overhead not justified)
- When abstraction and implementation always change together
- When the bridge adds more complexity than it solves
- For simple, fixed class hierarchies

## Common Mistakes
- Confusing Bridge with Adapter (Bridge is for design time, Adapter for runtime incompatibilities)
- Creating unnecessary bridges (YAGNI principle)
- Not truly separating concerns (abstraction and implementation still coupled)
- Wrong implementation choices (should be about variations, not inheritance)

## Framework / Library Usage
- **JDBC** - Driver interface bridges applications to databases
- **SLF4J** - Logging facade bridges to different logging implementations
- **AWT/Swing** - Component/ComponentPeer hierarchy
- **Android** - Camera/CameraImpl hierarchy
- **Java Collections** - List interface with different implementations

## System Design Use Cases
- **Multi-platform applications** - Different OS implementations
- **Database abstraction layers** - Support multiple databases
- **UI frameworks** - Separate UI logic from rendering engine
- **Device drivers** - Abstract hardware behind driver interface

## Interview One-Liner
Bridge decouples an abstraction from implementation using composition, allowing them to vary independently.

## Common Interview Questions
- How is Bridge different from Decorator?
- When would you use Bridge vs Adapter?
- Real-world system using Bridge pattern?
- How does Bridge reduce class complexity?
- Can you swap implementations at runtime with Bridge?

## Interview One-Liner
One-line summary of the pattern.

