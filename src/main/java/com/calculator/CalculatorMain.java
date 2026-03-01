package com.calculator;

/**
 * Main entry point for the Calculator application.
 */
public class CalculatorMain {

    public static void main(String[] args) {
        Calculator calc = new Calculator();

        double a = 20;
        double b = 5;

        System.out.println("============================");
        System.out.println("   Calculator Application   ");
        System.out.println("============================");
        System.out.println();
        System.out.println("Operands: a = " + a + ", b = " + b);
        System.out.println("----------------------------");
        System.out.println("Addition       : " + a + " + " + b + " = " + calc.add(a, b));
        System.out.println("Subtraction    : " + a + " - " + b + " = " + calc.sub(a, b));
        System.out.println("Multiplication : " + a + " * " + b + " = " + calc.mul(a, b));
        System.out.println("Division       : " + a + " / " + b + " = " + calc.div(a, b));
        System.out.println("============================");
    }
}
