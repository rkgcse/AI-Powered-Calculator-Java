package com.rkgcse.calculator.ai;

import com.rkgcse.calculator.core.CalculatorEngine;
import java.util.Locale;

/** Offline natural-language layer that converts common calculation phrases to expressions. */
public class NaturalLanguageEngine {
    private final CalculatorEngine calculator = new CalculatorEngine();

    public double evaluate(String input) {
        if (input == null || input.isBlank()) throw new IllegalArgumentException("Enter a calculation.");
        String text = input.toLowerCase(Locale.ROOT).trim();
        String expression = toExpression(text);
        return calculator.evaluateExpression(expression);
    }

    public Intent detectIntent(String text) {
        String t = text.toLowerCase(Locale.ROOT);
        if (t.contains("square root") || t.contains("sqrt")) return Intent.SQRT;
        if (t.contains("percent") || t.contains("%")) return Intent.PERCENT;
        if (t.matches(".*\\b(sin|sine)\\b.*")) return Intent.SIN;
        if (t.matches(".*\\b(cos|cosine)\\b.*")) return Intent.COS;
        if (t.matches(".*\\b(tan|tangent)\\b.*")) return Intent.TAN;
        if (t.contains("power") || t.contains("raised") || t.contains("exponent")) return Intent.POWER;
        if (t.contains("multiply") || t.contains("multiplied") || t.contains("times") || t.contains("product")) return Intent.MULTIPLY;
        if (t.contains("divide") || t.contains("divided") || t.contains("quotient")) return Intent.DIVIDE;
        if (t.contains("subtract") || t.contains("minus") || t.contains("difference")) return Intent.SUBTRACT;
        if (t.contains("add") || t.contains("plus") || t.contains("sum")) return Intent.ADD;
        if (t.matches(".*\\d+\\s*[+].*")) return Intent.ADD;
        if (t.matches(".*\\d+\\s*[-].*")) return Intent.SUBTRACT;
        if (t.matches(".*\\d+\\s*[*/].*")) return t.contains("/") ? Intent.DIVIDE : Intent.MULTIPLY;
        return Intent.UNKNOWN;
    }

    private String toExpression(String text) {
        String t = text.replace("what is", "")
                .replace("calculate", "")
                .replace("please", "")
                .replace("answer", "")
                .replace("the", "")
                .trim();

        if (t.contains("percent") || t.contains("%")) {
            String cleaned = t.replace("what", "").replace("percent of", "%*").replace("% of", "%*");
            cleaned = cleaned.replace("percent", "%").replaceAll("\\s+", "");
            if (cleaned.matches(".*%\\*[-+]?\\d+(?:\\.\\d+)?$")) {
                String[] parts = cleaned.split("%\\*", 2);
                return parts[0] + "%*" + parts[1];
            }
            return cleaned;
        }

        t = t.replace("square root of", "sqrt(")
                .replace("square root", "sqrt(")
                .replace("root of", "sqrt(")
                .replace("raised to the power of", "^")
                .replace("raised to", "^")
                .replace("to the power of", "^")
                .replace("power", "^")
                .replace("multiplied by", "*")
                .replace("multiply by", "*")
                .replace("times", "*")
                .replace("divided by", "/")
                .replace("divide by", "/")
                .replace("plus", "+")
                .replace("add", "+")
                .replace("minus", "-")
                .replace("subtract", "-")
                .replace("sine", "sin")
                .replace("cosine", "cos")
                .replace("tangent", "tan")
                .replaceAll("\\b(sin|cos|tan|sqrt|log|ln|abs|exp)\\s+([-+]?\\d+(?:\\.\\d+)?)", "$1($2)")
                .replaceAll("\\s+", "");

        if (t.startsWith("sqrt(") && !t.endsWith(")")) t += ")";
        return t;
    }
}
