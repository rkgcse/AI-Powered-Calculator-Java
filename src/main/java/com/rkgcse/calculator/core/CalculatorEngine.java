package com.rkgcse.calculator.core;

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
}
