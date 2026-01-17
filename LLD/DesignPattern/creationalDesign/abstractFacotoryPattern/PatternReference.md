# Abstract Factory Pattern — Interview Reference

## Intent
Provide an interface for creating families of related or dependent objects without specifying their concrete classes.

## Problem Statement
Applications need to work with multiple families of related objects (e.g., UI components for Windows vs macOS, database drivers for SQL vs NoSQL). Hardcoding concrete classes creates tight coupling and makes it difficult to switch between families or add new ones.

## Why Simple Code Fails
Using conditional logic (if/else) scattered throughout the code to decide which concrete class to instantiate leads to:
- Tight coupling between client code and concrete classes
- Difficult to add new product families without modifying existing code
- Hard to maintain consistency within a family of objects
- Violates Open/Closed Principle

## Solution Overview
Define an abstract factory interface that declares methods for creating abstract products. Implement concrete factories for each product family. This allows clients to work with product families through abstract interfaces without knowing concrete classes.

## Participants / Roles
- AbstractFactory (declares factory methods)
- ConcreteFactory (implements factory methods for a family)
- AbstractProduct (declares product interface)
- ConcreteProduct (implements specific product)

## Runtime Execution Flow
1. Client requests a factory for a specific family
2. Client uses factory methods to create products
3. Factory returns concrete products as abstract types
4. Client works with products through abstract interfaces

## Minimal Java Example
## Without Pattern

```java
// Tightly coupled - hard to switch UI families
if (osType.equals("WINDOWS")) {
  Button btn = new WindowsButton();
  Checkbox cb = new WindowsCheckbox();
} else if (osType.equals("MAC")) {
  Button btn = new MacButton();
  Checkbox cb = new MacCheckbox();
}
```

## With Pattern

```java
// Abstract factory
public abstract class EmployeeAbstractFactory {
  public abstract Employee createEmployee();
}

// Concrete factories for different developer types
public class WebDeveloperFactory extends EmployeeAbstractFactory {
  @Override
  public Employee createEmployee() {
    return new WebDeveloper();
  }
}

public class AndroidDeveloperFactory extends EmployeeAbstractFactory {
  @Override
  public Employee createEmployee() {
    return new AndroidDeveloper();
  }
}

// Product interface
public interface Employee {
  public int getSalary();
  public String name();
}

// Concrete products
public class WebDeveloper implements Employee {
  @Override
  public int getSalary() {
    return 12000;
  }
  
  @Override
  public String name() {
    return "Web Developer";
  }
}

public class AndroidDeveloper implements Employee {
  @Override
  public int getSalary() {
    return 15000;
  }
  
  @Override
  public String name() {
    return "Android Developer";
  }
}

// Factory method to create employees
public class EmployeeFactory {
  public static Employee getEmployee(EmployeeAbstractFactory factory) {
    return factory.createEmployee();
  }
}

// Client code
public class Client {
  public static void main(String args[]) {
    // Create Web Developer using factory
    Employee e1 = EmployeeFactory.getEmployee(new WebDeveloperFactory());
    System.out.println("Employee: " + e1.name() + ", Salary: " + e1.getSalary());
    
    // Create Android Developer using factory
    Employee e2 = EmployeeFactory.getEmployee(new AndroidDeveloperFactory());
    System.out.println("Employee: " + e2.name() + ", Salary: " + e2.getSalary());
  }
}

// Output:
// Employee: Web Developer, Salary: 12000
// Employee: Android Developer, Salary: 15000
```

## Advantages
- Isolates client from concrete classes (Dependency Inversion)
- Ensures products within a family work together consistently
- Makes it easy to add new product families
- Centralizes object creation logic
- Follows Open/Closed Principle (new families without modifying existing code)
- Improves code testability (use mock factories)

## Disadvantages
- Adds complexity with many abstract classes and interfaces
- Can be overkill for simple applications
- Makes class hierarchy deeper and harder to navigate
- Adding new product types requires modifying all concrete factories

## When NOT to Use
- When you only have one family of products
- When products don't need to work together as families
- When application is unlikely to support multiple product families
- Simple Factory or Factory Method patterns may be sufficient

## Common Mistakes
- Creating too many abstract layers upfront
- Confusing Abstract Factory with Factory Method
- Not ensuring consistency within product families
- Overusing the pattern for simple object creation

## Framework / Library Usage
- **Swing/AWT**: Different UI factories for different look-and-feels
- **JDBC**: Database connection factories for different databases
- **Spring**: BeanFactory and ApplicationContext as abstract factories
- **XML Parsers**: Different parser factories (DOM, SAX, StAX)

## System Design Use Cases
- Cross-platform UI applications (Windows/Mac/Linux)
- Database abstraction layers (MySQL, PostgreSQL, MongoDB)
- Cloud provider SDKs (AWS, Azure, GCP)
- Document generation (PDF, HTML, Excel)
- Game development (different rendering engines)

## Interview One-Liner
Abstract Factory provides an interface for creating families of related objects without coupling clients to concrete classes, ensuring products within a family work together consistently.

## Common Interview Questions

### Q1: What's the difference between Abstract Factory and Factory Method?

**Answer:**
| Aspect | Factory Method | Abstract Factory |
|--------|---|---|
| **Purpose** | Creates single product type | Creates families of related products |
| **Hierarchy** | Single class hierarchy | Multiple related hierarchies |
| **Usage** | `Creator.factoryMethod()` | `Factory.createProductA()` + `Factory.createProductB()` |
| **Example** | `ShapeFactory.getShape()` returns one Shape | `UIFactory` returns Button AND Checkbox together |
| **Coupling** | Reduces coupling to one product | Reduces coupling to related product families |

**Example Comparison:**
```java
// Factory Method - creates ONE product type
interface ShapeFactory {
  Shape createShape();  // only one method
}

// Abstract Factory - creates FAMILY of products
interface UIFactory {
  Button createButton();      // multiple methods
  Checkbox createCheckbox();
  TextField createTextField();
}
```

---

### Q2: How would you handle adding a new product type to an existing family?

**Answer:**
You must modify ALL concrete factories in the family. For example, if you want to add `IOSDeveloper` to the Employee factory family:

```java
// Original abstract factory
public abstract class EmployeeAbstractFactory {
  public abstract Employee createEmployee();
}

// After adding IOSDeveloper: create new factory
public class IOSDeveloperFactory extends EmployeeAbstractFactory {
  @Override
  public Employee createEmployee() {
    return new IOSDeveloper();  // NEW!
  }
}

// Concrete product
public class IOSDeveloper implements Employee {
  @Override
  public int getSalary() {
    return 16000;
  }
  
  @Override
  public String name() {
    return "iOS Developer";
  }
}

// Usage in client
public class Client {
  public static void main(String args[]) {
    // Existing developers
    Employee e1 = EmployeeFactory.getEmployee(new WebDeveloperFactory());
    System.out.println(e1.name() + ": $" + e1.getSalary());
    
    Employee e2 = EmployeeFactory.getEmployee(new AndroidDeveloperFactory());
    System.out.println(e2.name() + ": $" + e2.getSalary());
    
    // NEW iOS Developer - seamlessly added!
    Employee e3 = EmployeeFactory.getEmployee(new IOSDeveloperFactory());
    System.out.println(e3.name() + ": $" + e3.getSalary());
  }
}

// Output:
// Web Developer: $12000
// Android Developer: $15000
// iOS Developer: $16000
```

**Key Point:** Notice you DON'T need to modify EmployeeAbstractFactory, Employee, WebDeveloperFactory, or AndroidDeveloperFactory. You only add NEW concrete factories. This follows Open/Closed Principle!

---

### Q3: Can Abstract Factory be used with Singleton? How?

**Answer:**
Yes! You can create a singleton factory provider that returns the appropriate factory:

```java
// Singleton Factory Provider
public class EmployeeFactoryProvider {
  private static EmployeeFactoryProvider instance;
  private Map<String, EmployeeAbstractFactory> factories;
  
  private EmployeeFactoryProvider() {
    factories = new HashMap<>();
    factories.put("WEB", new WebDeveloperFactory());
    factories.put("ANDROID", new AndroidDeveloperFactory());
    factories.put("IOS", new IOSDeveloperFactory());
  }
  
  public static EmployeeFactoryProvider getInstance() {
    if (instance == null) {
      instance = new EmployeeFactoryProvider();
    }
    return instance;
  }
  
  public EmployeeAbstractFactory getFactory(String type) {
    return factories.get(type.toUpperCase());
  }
}

// Usage - only ONE factory provider instance in entire app
public class Client {
  public static void main(String args[]) {
    EmployeeFactoryProvider provider = EmployeeFactoryProvider.getInstance();
    
    // Get factories from singleton provider
    EmployeeAbstractFactory webFactory = provider.getFactory("WEB");
    Employee e1 = EmployeeFactory.getEmployee(webFactory);
    System.out.println(e1.name() + ": $" + e1.getSalary());
    
    EmployeeAbstractFactory androidFactory = provider.getFactory("ANDROID");
    Employee e2 = EmployeeFactory.getEmployee(androidFactory);
    System.out.println(e2.name() + ": $" + e2.getSalary());
  }
}

// Output:
// Web Developer: $12000
// Android Developer: $15000
```

**Benefits:**
- Single factory provider instance across application
- All factories managed in one place
- Easy to add new factories (just add to map)
- Thread-safe factory access (with synchronization)

---

### Q4: What problems does Abstract Factory solve that simple conditionals don't?

**Answer:**
Without Abstract Factory, you scatter creation logic everywhere with conditionals:

```java
// WITHOUT Abstract Factory - BAD!
public class EmployeeService {
  public void hireEmployee(String type) {
    Employee emp;
    
    if (type.equals("WEB")) {  // scattered logic
      emp = new WebDeveloper();
    } else if (type.equals("ANDROID")) {  // scattered logic
      emp = new AndroidDeveloper();
    } else if (type.equals("IOS")) {  // scattered logic
      emp = new IOSDeveloper();
    }
    
    System.out.println(emp.name() + " hired with salary: $" + emp.getSalary());
  }
}

public class PayrollService {
  public void calculatePayroll(String type) {
    Employee emp;
    
    if (type.equals("WEB")) {  // REPEATED logic!
      emp = new WebDeveloper();
    } else if (type.equals("ANDROID")) {  // REPEATED logic!
      emp = new AndroidDeveloper();
    } else if (type.equals("IOS")) {  // REPEATED logic!
      emp = new IOSDeveloper();
    }
    
    System.out.println("Payroll for " + emp.name() + ": $" + emp.getSalary());
  }
}

public class ReportService {
  public void generateReport(String type) {
    Employee emp;
    
    if (type.equals("WEB")) {  // REPEATED again!
      emp = new WebDeveloper();
    } else if (type.equals("ANDROID")) {  // REPEATED again!
      emp = new AndroidDeveloper();
    }
    
    System.out.println("Report: " + emp.name());
  }
}
```

**Problems:**
1. **Code duplication** - Same if/else logic in 3 different classes
2. **Hard to maintain** - Adding IOSDeveloper means changing 3 classes
3. **Hard to test** - Can't test without creating real objects
4. **Tight coupling** - All classes depend on concrete Employee classes
5. **Hard to extend** - Each new type requires finding all places with the if/else

**WITH Abstract Factory - GOOD!**
```java
// EmployeeFactory encapsulates all creation logic
public class EmployeeFactory {
  public static Employee getEmployee(EmployeeAbstractFactory factory) {
    return factory.createEmployee();
  }
}

// Now all services use the factory - NO duplication!
public class EmployeeService {
  public void hireEmployee(EmployeeAbstractFactory factory) {
    Employee emp = EmployeeFactory.getEmployee(factory);  // clean!
    System.out.println(emp.name() + " hired with salary: $" + emp.getSalary());
  }
}

public class PayrollService {
  public void calculatePayroll(EmployeeAbstractFactory factory) {
    Employee emp = EmployeeFactory.getEmployee(factory);  // reusable!
    System.out.println("Payroll for " + emp.name() + ": $" + emp.getSalary());
  }
}

public class ReportService {
  public void generateReport(EmployeeAbstractFactory factory) {
    Employee emp = EmployeeFactory.getEmployee(factory);  // consistent!
    System.out.println("Report: " + emp.name());
  }
}

// Usage - all services work the same way
public class Client {
  public static void main(String args[]) {
    EmployeeAbstractFactory webFactory = new WebDeveloperFactory();
    EmployeeAbstractFactory androidFactory = new AndroidDeveloperFactory();
    
    EmployeeService empService = new EmployeeService();
    PayrollService payroll = new PayrollService();
    ReportService report = new ReportService();
    
    // All services work with ANY factory - no coupling!
    empService.hireEmployee(webFactory);      // Works!
    payroll.calculatePayroll(androidFactory); // Works!
    report.generateReport(webFactory);        // Works!
  }
}

// Output:
// Web Developer hired with salary: $12000
// Payroll for Android Developer: $15000
// Report: Web Developer
```

**Benefits:**
1. **No code duplication** - Creation logic in ONE place (EmployeeFactory)
2. **Easy to maintain** - Adding IOSDeveloper requires NO changes to services
3. **Easy to test** - Pass mock factory to test
4. **Loose coupling** - Services depend only on EmployeeAbstractFactory interface
5. **Easy to extend** - New developer types automatically work with all services

**Key Insight:** Abstract Factory centralizes creation logic and makes all client code independent of concrete types!

---

### Q5: How do you ensure product families are consistent?

**Answer:**
The Abstract Factory pattern itself enforces consistency by design:

1. **Type system enforces it:**
```java
// Each factory creates one type of employee
public class WebDeveloperFactory extends EmployeeAbstractFactory {
  @Override
  public Employee createEmployee() {
    return new WebDeveloper();  // ALWAYS WebDeveloper!
  }
}

public class AndroidDeveloperFactory extends EmployeeAbstractFactory {
  @Override
  public Employee createEmployee() {
    return new AndroidDeveloper();  // ALWAYS AndroidDeveloper!
  }
}

// Impossible to mix - type system prevents it!
EmployeeAbstractFactory factory = new WebDeveloperFactory();
Employee emp = factory.createEmployee();  // Always WebDeveloper
```

2. **Real-world example with multiple products per family:**
```java
// If you had multiple products per factory (UI example):
public interface UIFactory {
  Button createButton();
  Checkbox createCheckbox();
}

public class WindowsUIFactory implements UIFactory {
  @Override
  public Button createButton() { 
    return new WindowsButton();      // Windows button
  }
  @Override
  public Checkbox createCheckbox() { 
    return new WindowsCheckbox();    // Windows checkbox (guaranteed match!)
  }
}

// IMPOSSIBLE to get mismatched products!
UIFactory factory = new WindowsUIFactory();
Button btn = factory.createButton();        // Windows
Checkbox cb = factory.createCheckbox();     // Windows (100% consistent!)

// You CANNOT do this:
// Button btn = new WindowsButton();       // Windows
// Checkbox cb = new MacCheckbox();        // Mac (ERROR - can't happen!)
```

3. **Validation for extra safety:**
```java
// Optional: add validation to ensure consistency
public class EmployeeValidator {
  public static void validateConsistency(List<Employee> employees) {
    if (employees.isEmpty()) return;
    
    String firstType = employees.get(0).getClass().getSimpleName();
    
    for (Employee emp : employees) {
      String empType = emp.getClass().getSimpleName();
      if (!empType.startsWith(firstType.split("Developer")[0])) {
        throw new IllegalStateException(
          "Inconsistent employee types: expected " + firstType + 
          " but got " + empType
        );
      }
    }
  }
}

// Usage
public class Client {
  public static void main(String args[]) {
    List<Employee> employees = new ArrayList<>();
    
    employees.add(EmployeeFactory.getEmployee(new WebDeveloperFactory()));
    employees.add(EmployeeFactory.getEmployee(new WebDeveloperFactory()));
    employees.add(EmployeeFactory.getEmployee(new WebDeveloperFactory()));
    
    // Validate all are consistent
    EmployeeValidator.validateConsistency(employees);
    System.out.println("All employees are consistent!");
  }
}
```

**Key Takeaway:** The pattern's structure naturally ensures consistency - each factory is responsible for one family, and you can't accidentally mix products from different families because each factory method has one specific implementation.

---

### Q6: How to avoid switch/if-else in factories?

**Answer:**
There are several approaches to eliminate if-else chains in factories:

**Approach 1: Map-based Registry (Recommended)**
```java
public class RegistryEmployeeFactory {
  private static Map<String, EmployeeAbstractFactory> registry = new HashMap<>();
  
  static {
    registry.put("web", new WebDeveloperFactory());
    registry.put("android", new AndroidDeveloperFactory());
    registry.put("ios", new IOSDeveloperFactory());
  }
  
  public Employee getEmployee(String type) {
    EmployeeAbstractFactory factory = registry.get(type.toLowerCase());
    if (factory == null) {
      throw new IllegalArgumentException("Unknown employee type: " + type);
    }
    return EmployeeFactory.getEmployee(factory);
  }
  
  // Easy to extend - no code modification needed!
  public static void registerFactory(String name, EmployeeAbstractFactory factory) {
    registry.put(name, factory);
  }
}

// Usage:
RegistryEmployeeFactory factory = new RegistryEmployeeFactory();
Employee emp = factory.getEmployee("web");  // No if-else!

// Later, add new developer type without modifying factory:
RegistryEmployeeFactory.registerFactory("devops", new DevOpsFactory());
```

**Approach 2: Enum-based Factory**
```java
public enum DeveloperType {
  WEB(WebDeveloperFactory::new),
  ANDROID(AndroidDeveloperFactory::new),
  IOS(IOSDeveloperFactory::new);
  
  private final Supplier<EmployeeAbstractFactory> supplier;
  
  DeveloperType(Supplier<EmployeeAbstractFactory> supplier) {
    this.supplier = supplier;
  }
  
  public Employee create() {
    return EmployeeFactory.getEmployee(supplier.get());
  }
}

// Usage:
Employee emp = DeveloperType.WEB.create();  // Type-safe!
```

**Approach 3: Strategy Pattern with Factory**
```java
public class StrategyEmployeeFactory {
  private Map<String, Supplier<EmployeeAbstractFactory>> strategies = new HashMap<>();
  
  public StrategyEmployeeFactory() {
    strategies.put("web", WebDeveloperFactory::new);
    strategies.put("android", AndroidDeveloperFactory::new);
    strategies.put("ios", IOSDeveloperFactory::new);
  }
  
  public Employee getEmployee(String type) {
    Supplier<EmployeeAbstractFactory> supplier = strategies.get(type.toLowerCase());
    if (supplier == null) throw new IllegalArgumentException("Unknown type");
    return EmployeeFactory.getEmployee(supplier.get());
  }
}
```

**Comparison:**

| Approach | Pros | Cons |
|----------|------|------|
| Map Registry | Simple, extensible | Reflection overhead |
| Enum | Type-safe, no strings | Fixed at compile time |
| Strategy | Flexible, testable | More objects |

**Best Practice:** Use **Map-based Registry** for most cases!

---

### Q7: When choose Factory vs Builder?

**Answer:**
Factory and Builder solve different problems. Choose based on your needs:

**Use Factory Pattern when:**
- Creating **simple objects** with few variations
- Need to **hide concrete class** details from client
- Creating objects **based on a type/condition**
- Object creation is **straightforward**

```java
// Factory makes sense here - simple creation of employee types
Employee emp = factory.getEmployee(new WebDeveloperFactory());
```

**Use Builder Pattern when:**
- Creating **complex objects** with many properties
- Object has **many optional parameters**
- Need to create **different representations** of same object
- Want to avoid **telescoping constructors**
- Need **immutable objects** with validation

```java
// Builder makes sense here - complex object
Student student = new StudentBuilder()
  .setRollNo(101)
  .setName("John")
  .setCourseName("B.Tech")
  .setAge(20)
  .setFatherName("Ram")
  .setEmailId("john@example.com")
  .build();
```

**Side-by-Side Comparison:**

| Aspect | Factory | Builder |
|--------|---------|---------|
| **Object Complexity** | Simple | Complex (many properties) |
| **Number of Parameters** | Few | Many (with optionals) |
| **Constructor Overloads** | 1-2 | Avoided (problem factory solves) |
| **Validation** | Optional | At build() time |
| **Immutability** | Not guaranteed | Can be guaranteed |
| **Creation Process** | One-shot | Step-by-step |
| **Family of Objects** | Different types | Different representations |

**Real-world Example:**

```java
// FACTORY: Simple type selection for employee families
public class EmployeeFactory {
  public Employee getEmployee(EmployeeAbstractFactory factory) {
    return factory.createEmployee();
  }
}

// BUILDER: Complex object construction for configurations
public class DatabaseConfigBuilder {
  private String host = "localhost";
  private int port = 3306;
  private String username = "root";
  private String password = "";
  private int poolSize = 10;
  private boolean ssl = false;
  
  public DatabaseConfigBuilder host(String host) {
    this.host = host;
    return this;
  }
  
  public DatabaseConfigBuilder port(int port) {
    this.port = port;
    return this;
  }
  
  public DatabaseConfig build() {
    validate();  // Ensure required fields are set
    return new DatabaseConfig(host, port, username, password, poolSize, ssl);
  }
}

// Usage:
// Factory for creating employee families
Employee webDev = new EmployeeFactory().getEmployee(new WebDeveloperFactory());

// Builder for complex configuration
DatabaseConfig config = new DatabaseConfigBuilder()
  .host("db.example.com")
  .port(5432)
  .username("admin")
  .password("secret")
  .build();
```

**Decision Tree:**

```
Do you need to create ONE of many different FAMILIES?
  ├─ YES → Use ABSTRACT FACTORY (Windows vs Mac UI family)
  └─ NO → Next question

Do you have many optional properties to set?
  ├─ YES → Use BUILDER (Student: name, age, email, phone, etc.)
  └─ NO → Direct constructor or simple factory
```

**Key Insight:**
- **Factory** = "What family of objects should I create?"
- **Builder** = "How should I construct this complex object step-by-step?"

You can even combine both:
```java
// Factory creates Employee via consistent configuration
public class EmployeeHiringService {
  public Employee hireDeveloper(String type, String name, int salary) {
    EmployeeAbstractFactory factory = getFactory(type);
    Employee emp = factory.createEmployee();
    // Could use builder for complex employee setup
    return emp;
  }
}
```
