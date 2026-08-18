# AI-Powered Calculator Java

A student-friendly Java calculator that combines scientific calculations with lightweight natural-language intent recognition.

## Features

- Basic arithmetic: `+`, `-`, `*`, `/`
- Scientific operations: powers, square root, percentages, trigonometry
- Natural-language calculations such as:
  - `what is 25% of 840`
  - `calculate square root of 144`
  - `what is 12 multiplied by 8`
  - `sin 30`
- Calculation history
- Friendly validation and error messages
- Java Swing desktop interface
- Unit tests with JUnit 5
- Maven project structure

## AI / ML Component

This project uses a lightweight **rule-based natural-language intent classifier** rather than an external API or pretrained model. The classifier identifies the user's calculation intent from keywords and extracts numerical entities using regular expressions. This keeps the application fully offline and easy to understand for a Java student project.

## Tech Stack

- Java 17+
- Swing
- Maven
- JUnit 5

## Run

### Prerequisites

- JDK 17 or later
- Maven 3.8+

### Start the application

```bash
mvn clean package
java -jar target/ai-powered-calculator-java-1.0.0.jar
```

### Run tests

```bash
mvn test
```

## Project Structure

```text
src/
 ├── main/java/com/rkgcse/calculator/
 │   ├── Main.java
 │   ├── ai/
 │   │   ├── Intent.java
 │   │   └── NaturalLanguageEngine.java
 │   ├── core/
 │   │   └── CalculatorEngine.java
 │   ├── history/
 │   │   └── CalculationHistory.java
 │   └── ui/
 │       └── CalculatorFrame.java
 └── test/java/com/rkgcse/calculator/
     ├── ai/NaturalLanguageEngineTest.java
     └── core/CalculatorEngineTest.java
```

## Example Inputs

| Input | Result |
|---|---:|
| `25 + 35` | `60` |
| `25% of 840` | `210` |
| `square root of 144` | `12` |
| `12 multiplied by 8` | `96` |
| `sin 30` | `0.5` |
| `2 power 5` | `32` |

## Learning Goals

This project demonstrates Java OOP, interfaces between application layers, Swing UI development, regex-based NLP, unit testing, exception handling, and Maven project management.

## License

MIT
