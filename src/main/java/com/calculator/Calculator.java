package com.calculator;

/**
 * Calculator class providing basic arithmetic operations.
 */
public class Calculator {

    /**
     * Adds two numbers.
     *
     * @param a first operand
     * @param b second operand
     * @return sum of a and b
     */
    public double add(double a, double b) {
        return a + b;
    }

    /**
     * Subtracts second number from first.
     *
     * @param a first operand
     * @param b second operand
     * @return difference of a and b
     */
    public double sub(double a, double b) {
        return a - b;
    }

    /**
     * Multiplies two numbers.
     *
     * @param a first operand
     * @param b second operand
     * @return product of a and b
     */
    public double mul(double a, double b) {
        return a * b;
    }

    /**
     * Divides first number by second.
     *
     * @param a dividend
     * @param b divisor
     * @return quotient of a divided by b
     * @throws ArithmeticException if b is zero
     */
    public double div(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }
}
