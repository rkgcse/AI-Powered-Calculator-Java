package com.rkgcse.calculator.ai;

import com.rkgcse.calculator.core.CalculatorEngine;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NaturalLanguageEngine {
    private static final Pattern NUMBER = Pattern.compile("[-+]?\\d+(?:\\.\\d+)?");

    public double evaluate(String input) {
        if (input == null || input.isBlank()) throw new IllegalArgumentException("Enter a calculation.");
        String text = input.toLowerCase(Locale.ROOT).trim();
        Intent intent = detectIntent(text);
        double[] values = numbers(text);
        CalculatorEngine engine = new CalculatorEngine();
        return switch (intent) {
            case ADD -> require(values, 2, engine::add);
            case SUBTRACT -> require(values, 2, engine::subtract);
            case MULTIPLY -> require(values, 2, engine::multiply);
            case DIVIDE -> require(values, 2, engine::divide);
            case POWER -> require(values, 2, engine::power);
            case SQRT -> engine.sqrt(requireOne(values));
            case PERCENT -> {
                if (values.length == 1) yield values[0] / 100.0;
                yield engine.percentOf(values[1], values[0]);
            }
            case SIN -> engine.sin(requireOne(values));
            case COS -> engine.cos(requireOne(values));
            case TAN -> engine.tan(requireOne(values));
            default -> throw new IllegalArgumentException("I could not understand that calculation.");
        };
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

    private double[] numbers(String text) {
        Matcher matcher = NUMBER.matcher(text.replace("%", ""));
        java.util.List<Double> result = new java.util.ArrayList<>();
        while (matcher.find()) result.add(Double.parseDouble(matcher.group()));
        return result.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private double requireOne(double[] values) {
        if (values.length < 1) throw new IllegalArgumentException("Please provide a number.");
        return values[0];
    }

    private double require(double[] values, int count, java.util.function.DoubleBinaryOperator op) {
        if (values.length < count) throw new IllegalArgumentException("Please provide two numbers.");
        return op.applyAsDouble(values[0], values[1]);
    }
}
