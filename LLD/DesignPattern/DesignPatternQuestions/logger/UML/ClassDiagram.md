# Chain of Responsibility - Class Diagram

```mermaid
classDiagram
    class LogProcessor {
        <<abstract>>
        -nextProcessor: LogProcessor
        +setNextProcessor(LogProcessor)
        +logMessage(int level, String message)
        #write(String message)
    }

    class DebugLogProcessor {
        #write(String message)
    }

    class InfoLogProcessor {
        #write(String message)
    }

    class ErrorLogProcessor {
        #write(String message)
    }

    LogProcessor <|-- DebugLogProcessor
    LogProcessor <|-- InfoLogProcessor
    LogProcessor <|-- ErrorLogProcessor
    LogProcessor --> LogProcessor: nextProcessor
```

## Class Relationships

| Class | Responsibility | Depends On |
|-------|---|---|
| **LogProcessor** | Abstract base - defines chain structure and template for logging | nextProcessor (self-reference) |
| **DebugLogProcessor** | Handles debug-level log messages | LogProcessor (extends) |
| **InfoLogProcessor** | Handles info-level log messages | LogProcessor (extends) |
| **ErrorLogProcessor** | Handles error-level log messages | LogProcessor (extends) |

## How to Code This Pattern

1. **Create Abstract Processor**: Define `LogProcessor` with next processor reference
2. **Implement Processors**: Each concrete class handles specific log level
3. **Build Chain**: Connect processors in order (Debug → Info → Error)
4. **Send Request**: Processor either handles it or passes to next
