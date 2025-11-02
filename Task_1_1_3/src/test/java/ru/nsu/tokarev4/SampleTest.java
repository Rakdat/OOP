package ru.nsu.tokarev4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Юнит-тесты для математических выражений.
 */
public class SampleTest {

    @Test
    public void testNumberEvaluation() {
        Expression number = new Number(5);
        assertEquals(5, number.eval(""));
        assertEquals(5, number.eval("x=10"));
    }

    @Test
    public void testVariableEvaluation() {
        Expression variable = new Variable("x");
        assertEquals(10, variable.eval("x=10"));
        assertEquals(5, variable.eval("x=5; y=3"));

        assertThrows(IllegalArgumentException.class, () -> {
            variable.eval("y=10"); // x не определен
        });
    }

    @Test
    public void testAddition() {
        Expression add = new Add(new Number(3), new Number(5));
        assertEquals(8, add.eval(""));

        Expression addWithVars = new Add(new Variable("x"), new Number(2));
        assertEquals(7, addWithVars.eval("x=5"));
    }

    @Test
    public void testSubtraction() {
        Expression sub = new Sub(new Number(10), new Number(4));
        assertEquals(6, sub.eval(""));

        Expression subWithVars = new Sub(new Variable("x"), new Variable("y"));
        assertEquals(2, subWithVars.eval("x=7; y=5"));
    }

    @Test
    public void testMultiplication() {
        Expression mul = new Mul(new Number(3), new Number(4));
        assertEquals(12, mul.eval(""));

        Expression mulWithVars = new Mul(new Variable("x"), new Variable("y"));
        assertEquals(20, mulWithVars.eval("x=4; y=5"));
    }

    @Test
    public void testDivision() {
        Expression div = new Div(new Number(15), new Number(3));
        assertEquals(5, div.eval(""));

        Expression divWithVars = new Div(new Variable("x"), new Variable("y"));
        assertEquals(4, divWithVars.eval("x=12; y=3"));

        // Тест деления на ноль
        Expression divByZero = new Div(new Number(10), new Number(0));
        assertThrows(ArithmeticException.class, () -> {
            divByZero.eval("");
        });
    }

    @Test
    public void testComplexExpression() {
        // (3 + (2 * x))
        Expression complex = new Add(
                new Number(3),
                new Mul(new Number(2), new Variable("x"))
        );

        assertEquals(13, complex.eval("x=5"));
        assertEquals(23, complex.eval("x=10"));
    }

    @Test
    public void testNumberDerivative() {
        Expression number = new Number(5);
        Expression deriv = number.derivative("x");

        assertTrue(deriv instanceof Number);
        assertEquals(0, ((Number) deriv).getValue());
        assertEquals(0, deriv.eval(""));
    }

    @Test
    public void testVariableDerivative() {
        Expression variableX = new Variable("x");
        Expression derivX = variableX.derivative("x");
        Expression derivY = variableX.derivative("y");

        // Производная по x должна быть 1
        assertTrue(derivX instanceof Number);
        assertEquals(1, ((Number) derivX).getValue());

        // Производная по y должна быть 0
        assertTrue(derivY instanceof Number);
        assertEquals(0, ((Number) derivY).getValue());
    }

    @Test
    public void testAdditionDerivative() {
        // (x + 5)' = 1 + 0 = 1
        Expression add = new Add(new Variable("x"), new Number(5));
        Expression deriv = add.derivative("x");

        assertEquals(1, deriv.eval("x=10")); // Производная константа
    }

    @Test
    public void testMultiplicationDerivative() {
        // (x * y)' = 1*y + x*0 = y
        Expression mul = new Mul(new Variable("x"), new Variable("y"));
        Expression deriv = mul.derivative("x");

        assertEquals(5, deriv.eval("x=3; y=5")); // Должно быть равно y
    }

    @Test
    public void testComplexDerivative() {
        // (3 + (2 * x))' = 0 + (0*x + 2*1) = 2
        Expression complex = new Add(
                new Number(3),
                new Mul(new Number(2), new Variable("x"))
        );

        Expression deriv = complex.derivative("x");
        assertEquals(2, deriv.eval("x=10")); // Производная всегда 2
    }

    @Test
    public void testExpressionParsing() {
        Expression parsed = ExpressionParser.parse("(3+(2*x))");
        assertNotNull(parsed);
        assertTrue(parsed instanceof Add);

        assertEquals(13, parsed.eval("x=5"));
    }

    @Test
    public void testExpressionToString() {
        Expression expr = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        String result = expr.toString();
        assertEquals("(3+(2*x))", result);
    }

    @Test
    public void testMultiCharacterVariables() {
        Expression expr = new Add(new Variable("var1"), new Variable("var2"));
        assertEquals(15, expr.eval("var1=7; var2=8"));

        Expression deriv = expr.derivative("var1");
        assertEquals(1, deriv.eval("")); // Производная по var1 = 1
    }

    @Test
    public void testNestedExpressions() {
        // ((x+2)*(y-1))
        Expression nested = new Mul(new Add(new Variable("x"), new Number(2)), new Sub(new Variable("y"), new Number(1)));

        assertEquals(25, nested.eval("x=3; y=6"));
    }
}