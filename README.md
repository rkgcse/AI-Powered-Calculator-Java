# AI-Powered Calculator Java

A polished Java Swing calculator designed for everyday calculations, scientific maths, and natural-language commands.

## Highlights

- Beautiful light, centered interface
- Animated hover/press buttons
- Full 0–9 keypad and basic operators
- Parentheses and order of operations
- Dedicated `=` result flow
- Scientific mode with one-click switching
- Natural-language AI-style calculation commands
- Celebration confetti animation after every successful result
- Three-note congratulatory success tone generated in Java
- Calculation history preview
- Keyboard Enter = calculate, Escape = clear
- JUnit 5 test coverage

## Operations

### Everyday mode

`+` `−` `×` `÷` `%` `(` `)` decimal values and negative numbers.

### Scientific mode

- Square root: `sqrt(144)`
- Powers: `2^8`
- Factorial: `5!`
- Trigonometry in degrees: `sin(30)`, `cos(60)`, `tan(45)`
- Logarithm: `log(100)`
- Natural logarithm: `ln(2.718281828)`
- Absolute value: `abs(-25)`
- Exponential: `exp(2)`
- Constants: `pi`, `e`

## Natural-language examples

The AI button understands commands such as:

- `25% of 840` → `210`
- `what is 12 multiplied by 8` → `96`
- `square root of 144` → `12`
- `2 power 5` → `32`
- `sin 30` → `0.5`
- `20 divided by 4` → `5`
- `15 plus 27` → `42`

The `=` button also tries the natural-language engine automatically if the normal expression parser cannot understand the input.

## AI / ML Component

The project uses a lightweight offline **natural-language intent layer** rather than pretending to use a pretrained machine-learning model. It recognizes common mathematical phrases and converts them into expressions understood by the calculator engine. This makes the project transparent, offline, and easy for a Java student to study and extend.

## Tech Stack

- Java 17+
- Java Swing
- Maven
- JUnit 5
- Java Sound API

## Run

### Prerequisites

- JDK 17 or later
- Maven 3.8+

### Tests

```bash
mvn clean test
```

### Build and run

```bash
mvn clean package
java -jar target/ai-powered-calculator-java-1.0.0.jar
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
 │       ├── AnimatedButton.java
 │       ├── CalculatorFrame.java
 │       ├── CelebrationPanel.java
 │       └── CelebrationSound.java
 └── test/java/com/rkgcse/calculator/
     ├── ai/NaturalLanguageEngineTest.java
     └── core/CalculatorEngineTest.java
```

## Learning Goals

This project demonstrates Java OOP, recursive-descent expression parsing, Swing UI development, event-driven animations, natural-language intent recognition, Java audio generation, unit testing, exception handling, and Maven project management.

## License

MIT

---

**made with ❤️ by Raushan kumar**
