package com.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Calculator class.
 */
class CalculatorTest {

    private Calculator calc;

    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }

    // ---- Addition Tests ----

    @Test
    @DisplayName("add: positive numbers")
    void testAddPositive() {
        assertEquals(5.0, calc.add(2.0, 3.0));
    }

    @Test
    @DisplayName("add: negative numbers")
    void testAddNegative() {
        assertEquals(-5.0, calc.add(-2.0, -3.0));
    }

    @Test
    @DisplayName("add: zero")
    void testAddZero() {
        assertEquals(3.0, calc.add(3.0, 0.0));
    }

    // ---- Subtraction Tests ----

    @Test
    @DisplayName("sub: positive result")
    void testSubPositive() {
        assertEquals(7.0, calc.sub(10.0, 3.0));
    }

    @Test
    @DisplayName("sub: negative result")
    void testSubNegative() {
        assertEquals(-5.0, calc.sub(3.0, 8.0));
    }

    @Test
    @DisplayName("sub: zero result")
    void testSubZero() {
        assertEquals(0.0, calc.sub(5.0, 5.0));
    }

    // ---- Multiplication Tests ----

    @Test
    @DisplayName("mul: positive numbers")
    void testMulPositive() {
        assertEquals(15.0, calc.mul(3.0, 5.0));
    }

    @Test
    @DisplayName("mul: with zero")
    void testMulZero() {
        assertEquals(0.0, calc.mul(5.0, 0.0));
    }

    @Test
    @DisplayName("mul: negative numbers")
    void testMulNegative() {
        assertEquals(6.0, calc.mul(-2.0, -3.0));
    }

    // ---- Division Tests ----

    @Test
    @DisplayName("div: exact division")
    void testDivExact() {
        assertEquals(4.0, calc.div(20.0, 5.0));
    }

    @Test
    @DisplayName("div: fractional result")
    void testDivFraction() {
        assertEquals(2.5, calc.div(5.0, 2.0));
    }

    @Test
    @DisplayName("div: divide by zero throws exception")
    void testDivByZero() {
        ArithmeticException ex = assertThrows(ArithmeticException.class,
                () -> calc.div(10.0, 0.0));
        assertEquals("Cannot divide by zero", ex.getMessage());
    }
}
