package com.rkgcse.calculator.ai;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NaturalLanguageEngineTest {
    private final NaturalLanguageEngine engine = new NaturalLanguageEngine();

    @Test void understandsPercentage() { assertEquals(210, engine.evaluate("25% of 840")); }
    @Test void understandsSquareRoot() { assertEquals(12, engine.evaluate("square root of 144")); }
    @Test void understandsMultiplicationWords() { assertEquals(96, engine.evaluate("12 multiplied by 8")); }
    @Test void understandsPower() { assertEquals(32, engine.evaluate("2 power 5")); }
    @Test void identifiesIntent() { assertEquals(Intent.DIVIDE, engine.detectIntent("20 divided by 5")); }
}
