package com.rkgcse.calculator.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalculationHistory {
    private final List<String> entries = new ArrayList<>();

    public void add(String expression, double result) {
        entries.add(expression + " = " + result);
    }

    public List<String> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public void clear() { entries.clear(); }
}
