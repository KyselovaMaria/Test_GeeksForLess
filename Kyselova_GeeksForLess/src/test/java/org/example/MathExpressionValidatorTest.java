package org.example;


import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;


public class MathExpressionValidatorTest {

    @Test
    public void testValidExpression() {
        assertTrue(MathExpressionValidator.isValidExpression("(2+3)*X=10"));
    }

    @Test
    public void testInvalidExpression() {
        assertFalse(MathExpressionValidator.isValidExpression("(2+3*X=10"));
    }

    @Test
    public void testValidCharsAndSequence() {
        assertTrue(MathExpressionValidator.isValidCharsAndSequence("2+3*X=10"));
    }

    @Test
    public void testInvalidCharsAndSequence() {
        assertFalse(MathExpressionValidator.isValidCharsAndSequence("2+3*^X=10"));
    }

    @Test
    public void testEvaluateExpression() {
        assertTrue(MathExpressionValidator.evaluateExpression("X=5", 5));
    }

    @Test
    public void testCountNumbers() {
        assertEquals(3, MathExpressionValidator.countNumbers("2+3.5*X=10.0"));
    }
}
