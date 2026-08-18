package com.rkgcse.calculator.core;

import java.util.Locale;

/**
 * Core calculator engine. Supports arithmetic, percentages, powers,
 * parentheses, constants, trigonometry, logarithms, factorial and more.
 */
public class CalculatorEngine {
    public double add(double a, double b) { return a + b; }
    public double subtract(double a, double b) { return a - b; }
    public double multiply(double a, double b) { return a * b; }
    public double divide(double a, double b) {
        if (b == 0) throw new ArithmeticException("Cannot divide by zero.");
        return a / b;
    }
    public double power(double a, double b) { return Math.pow(a, b); }
    public double sqrt(double a) {
        if (a < 0) throw new ArithmeticException("Square root of a negative number is not supported.");
        return Math.sqrt(a);
    }
    public double percentOf(double percent, double value) { return (percent / 100.0) * value; }
    public double sin(double degrees) { return Math.sin(Math.toRadians(degrees)); }
    public double cos(double degrees) { return Math.cos(Math.toRadians(degrees)); }
    public double tan(double degrees) { return Math.tan(Math.toRadians(degrees)); }
    public double log10(double value) {
        if (value <= 0) throw new ArithmeticException("Logarithm requires a positive number.");
        return Math.log10(value);
    }
    public double ln(double value) {
        if (value <= 0) throw new ArithmeticException("Natural logarithm requires a positive number.");
        return Math.log(value);
    }
    public double abs(double value) { return Math.abs(value); }
    public double factorial(double value) {
        if (value < 0 || value != Math.rint(value) || value > 170) {
            throw new ArithmeticException("Factorial requires a whole number from 0 to 170.");
        }
        double answer = 1;
        for (int i = 2; i <= (int) value; i++) answer *= i;
        return answer;
    }

    /** Evaluates an expression such as 2*(5+3), sqrt(144), sin(30), 5!, 20% or 2^8. */
    public double evaluateExpression(String expression) {
        if (expression == null || expression.isBlank()) throw new IllegalArgumentException("Enter a calculation.");
        String normalized = expression.toLowerCase(Locale.ROOT)
                .replace("×", "*").replace("÷", "/").replace("−", "-").replace("π", "pi");
        double value = new Parser(normalized).parse();
        if (Double.isNaN(value) || Double.isInfinite(value)) throw new ArithmeticException("Result is not a finite number.");
        return value;
    }

    private final class Parser {
        private final String text;
        private int pos;
        Parser(String text) { this.text = text.replaceAll("\\s+", ""); }

        double parse() {
            double value = expression();
            if (pos != text.length()) throw error("Unexpected character '" + text.charAt(pos) + "'");
            return value;
        }
        double expression() {
            double value = term();
            while (pos < text.length()) {
                char c = text.charAt(pos);
                if (c == '+') { pos++; value += term(); }
                else if (c == '-') { pos++; value -= term(); }
                else break;
            }
            return value;
        }
        double term() {
            double value = powerExpression();
            while (pos < text.length()) {
                char c = text.charAt(pos);
                if (c == '*') { pos++; value *= powerExpression(); }
                else if (c == '/') { pos++; value = divide(value, powerExpression()); }
                else break;
            }
            return value;
        }
        double powerExpression() {
            double value = unary();
            if (pos < text.length() && text.charAt(pos) == '^') {
                pos++;
                value = Math.pow(value, powerExpression());
            }
            return value;
        }
        double unary() {
            if (pos < text.length() && text.charAt(pos) == '+') { pos++; return unary(); }
            if (pos < text.length() && text.charAt(pos) == '-') { pos++; return -unary(); }
            return postfix();
        }
        double postfix() {
            double value = primary();
            while (pos < text.length()) {
                if (text.charAt(pos) == '%') { pos++; value /= 100.0; }
                else if (text.charAt(pos) == '!') { pos++; value = factorial(value); }
                else break;
            }
            return value;
        }
        double primary() {
            if (pos >= text.length()) throw error("A number or function is expected.");
            if (text.charAt(pos) == '(') {
                pos++;
                double value = expression();
                expect(')');
                return value;
            }
            if (Character.isLetter(text.charAt(pos))) {
                String name = readName();
                if (name.equals("pi")) return Math.PI;
                if (name.equals("e")) return Math.E;
                expect('(');
                double argument = expression();
                expect(')');
                return switch (name) {
                    case "sqrt" -> sqrt(argument);
                    case "sin" -> sin(argument);
                    case "cos" -> cos(argument);
                    case "tan" -> tan(argument);
                    case "log" -> log10(argument);
                    case "ln" -> ln(argument);
                    case "abs" -> abs(argument);
                    case "exp" -> Math.exp(argument);
                    default -> throw error("Unknown function: " + name);
                };
            }
            return number();
        }
        double number() {
            int start = pos;
            boolean dot = false;
            while (pos < text.length()) {
                char c = text.charAt(pos);
                if (Character.isDigit(c)) pos++;
                else if (c == '.' && !dot) { dot = true; pos++; }
                else break;
            }
            if (start == pos) throw error("A number is expected.");
            return Double.parseDouble(text.substring(start, pos));
        }
        String readName() {
            int start = pos;
            while (pos < text.length() && Character.isLetter(text.charAt(pos))) pos++;
            return text.substring(start, pos);
        }
        void expect(char expected) {
            if (pos >= text.length() || text.charAt(pos) != expected) throw error("Expected '" + expected + "'.");
            pos++;
        }
        IllegalArgumentException error(String message) { return new IllegalArgumentException(message); }
    }
}
