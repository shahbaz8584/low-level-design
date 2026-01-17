# Design Patterns Documentation

This folder contains the generated HTML documentation for all design patterns.

## Files

- **All_Design_Patterns_index.html** - Main entry point with all patterns overview
- **All_Design_Patterns_Creational.html** - Creational patterns (Factory, Builder, Singleton, Prototype, Abstract Factory)
- **All_Design_Patterns_Structural.html** - Structural patterns (Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy)
- **All_Design_Patterns_Behavioral.html** - Behavioral patterns (Observer, Strategy, State, Template, Chain of Responsibility, Memento, Null Object)
- **All_Design_Patterns_*.html** - Individual pattern pages with UML diagrams and interview Q&A

## How to Use

1. Start with `All_Design_Patterns_index.html`
2. Browse patterns by category (Creational, Structural, Behavioral)
3. Click on any pattern to view:
   - Pattern explanation
   - UML Class Diagram
   - Real-world examples
   - Interview Q&A (Q1-Q7 with answers)
   - Advantages and disadvantages

## Features

- ✅ Interactive Q&A accordions with expand/collapse
- ✅ UML Class Diagrams (Mermaid)
- ✅ Comprehensive interview questions for each pattern
- ✅ Code examples in Java
- ✅ Search functionality
- ✅ Responsive design

## Generated Files

These HTML files are automatically generated from markdown source files in `LLD/DesignPattern/`. Do not edit them directly - modify the `.md` files instead and regenerate.

To regenerate all pages:
```bash
python3 tools/generate_pattern_pages.py
```
