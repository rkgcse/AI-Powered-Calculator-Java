package com.rkgcse.calculator.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorEngineTest {
    private final CalculatorEngine calculator = new CalculatorEngine();

    @Test void addsNumbers() { assertEquals(8, calculator.add(3, 5)); }
    @Test void multipliesNumbers() { assertEquals(24, calculator.multiply(3, 8)); }
    @Test void dividesNumbers() { assertEquals(4, calculator.divide(20, 5)); }
    @Test void calculatesPercentage() { assertEquals(210, calculator.percentOf(25, 840)); }
    @Test void calculatesPower() { assertEquals(32, calculator.power(2, 5)); }
    @Test void rejectsDivisionByZero() { assertThrows(ArithmeticException.class, () -> calculator.divide(4, 0)); }
}
