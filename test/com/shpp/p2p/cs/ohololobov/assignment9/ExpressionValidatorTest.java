package com.shpp.p2p.cs.ohololobov.assignment9;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionValidatorTest {


    @ParameterizedTest
    @CsvSource(
            {
                    "2+3/4",
                    "-2/5",
                    "2.2",
                    "-2",
                    "-2a",
                    "-a*2",
            }
    )
    void isValidExpressionAssertTrueTest(String expression) {
        assertTrue(ExpressionValidator.isValidExpression(expression));
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "+2/5",
                    "-+2/5",
                    "2+3/",
                    "2//3",
                    "g/",
                    ":",
                    "&",
            }
    )
    void isValidExpressionCheckExceptionTest(String expression) {
        assertThrows(IllegalArgumentException.class, ()
                -> ExpressionValidator.isValidExpression(expression)
        );
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "#",
                    "а",//сyrillic letter
                    "|",
                    "'",
                    ":",
                    "&",
                    "9.",
                    ".9"
            }
    )
    void validateValidStructuresInExceptionTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.VALID_STRUCTURES_REG_EX;

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ExpressionValidator.validate(expression, validationRegEx)
        );
        assertEquals("Illegal component is present", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "-",
                    "9.9",
                    "(",
                    "a",
                    "2+3/4",
                    "-2/5",
                    "2.2",
                    "-2",
                    "-2a",
                    "-a*2",
            }
    )
    void validateValidStructuresTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.VALID_STRUCTURES_REG_EX;
        assertTrue(ExpressionValidator.validate(expression, validationRegEx));
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "#",
                    "_",
                    "/",
                    "'-",
                    ":",
                    "*",
                    ".",
                    ")",
            }
    )
    void validateBeginningOfExpressionExceptionTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.VALID_BEGINNING_REG_EX;

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ExpressionValidator.validate(expression, validationRegEx)
        );
        System.out.println(exception.getMessage());
        assertEquals("Illegal structure at start of expression", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "9.9",
                    "(",
                    "a",
                    "2+3/4",
                    "-2/5",
                    "2.2",
                    "-2",
                    "-2a",
                    "-a*2",
                    "-2+d",
            }
    )
    void validateBeginningOfExpressionTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.VALID_BEGINNING_REG_EX;

        assertTrue(ExpressionValidator.validate(expression, validationRegEx));
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "/",
                    "-",
                    "*",
                    ".",
                    "(",
                    "9in",
                    "+in",
            }
    )
    void validateInvalidEndRegExExceptionTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.INVALID_END_REG_EX;

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ExpressionValidator.validate(expression, validationRegEx)
        );
        System.out.println(exception.getMessage());
        assertEquals("Illegal structure at end of expression", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "9.9",
                    ")",
                    "5a",
                    "+sin",
            }
    )
    void validateInvalidEndRegExTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.INVALID_END_REG_EX;

        assertTrue(ExpressionValidator.validate(expression, validationRegEx));
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "sina",
                    "cos9",
                    "tan*",
                    "ctan)",
                    "atan.",
            }
    )
    void validateInvalidComponentAfterMathFunctionRegExExceptionTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.INVALID_COMPONENT_AFTER_MATH_FUNCTION_REG_EX;

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ExpressionValidator.validate(expression, validationRegEx)
        );
        System.out.println(exception.getMessage());
        assertEquals("Illegal component after function", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "sin(",
                    "atan(",
                    "cos(",
            }
    )
    void validateInvalidComponentAfterMathFunctionRegExTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.INVALID_COMPONENT_AFTER_MATH_FUNCTION_REG_EX;

        assertTrue(ExpressionValidator.validate(expression, validationRegEx));
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "+4",
                    ")",
                    "--",
                    "-/)",
                    ".",
                    ".9",
            }
    )
    void validateInvalidBeginningRegExExceptionTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.INVALID_BEGINNING_REG_EX;

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ExpressionValidator.validate(expression, validationRegEx)
        );
        System.out.println(exception.getMessage());
        assertEquals("Illegal structure at start of expression", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "-4",
                    "4",
                    "a",
                    "sin",
                    "4a",
                    "-a",
            }
    )
    void validateInvalidBeginningRegExTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.INVALID_BEGINNING_REG_EX;

        assertTrue(ExpressionValidator.validate(expression, validationRegEx));
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "94.+",
                    "-.778",
                    "-.",
                    "./)",
                    "(.9",
                    ").9",
                    "9.(",
                    "9.a",
                    "9.^",
                    ".a",
                    "a.",
            }
    )
    void validateInvalidDecimalSeparatorUsingRegExExceptionTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.INVALID_DECIMAL_SEPARATOR_USING_REG_EX;

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ExpressionValidator.validate(expression, validationRegEx)
        );
        System.out.println(exception.getMessage());
        assertEquals("Illegal decimal separator using", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "9.5",
                    "99.6",
                    "1.88",
                    "11.00",
            }
    )
    void validateInvalidDecimalSeparatorUsingRegExTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.INVALID_DECIMAL_SEPARATOR_USING_REG_EX;

        assertTrue(ExpressionValidator.validate(expression, validationRegEx));
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "()",
                    "(/",
                    "(*",
                    "(+",
                    "(^",
            }
    )
    void validateInvalidComponentAfterOpeningBracketRegExExceptionTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.INVALID_COMPONENT_AFTER_OPENING_BRACKET_REG_EX;

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ExpressionValidator.validate(expression, validationRegEx)
        );
        System.out.println(exception.getMessage());
        assertEquals("Illegal component after opening bracket", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "(9.5",
                    "(a",
                    "(-",
            }
    )
    void validateInvalidComponentAfterOpeningBracketRegExTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.INVALID_COMPONENT_AFTER_OPENING_BRACKET_REG_EX;

        assertTrue(ExpressionValidator.validate(expression, validationRegEx));
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "--",
                    "+/",
                    "+)",
                    "^.",
            }
    )
    void validateInvalidComponentAfterOperatorRegExExceptionTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.INVALID_COMPONENT_AFTER_OPERATOR_REG_EX;

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ExpressionValidator.validate(expression, validationRegEx)
        );
        System.out.println(exception.getMessage());
        assertEquals("Illegal component after operator", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "+9",
                    "-a",
                    "*(",
            }
    )
    void validateInvalidComponentAfterOperatorRegExTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.INVALID_COMPONENT_AFTER_OPERATOR_REG_EX;

        assertTrue(ExpressionValidator.validate(expression, validationRegEx));
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "a9",
                    "aa9",
                    "a.",
            }
    )
    void validateInvalidComponentAfterVariableRegExExceptionTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.INVALID_COMPONENT_AFTER_VARIABLE_REG_EX;

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ExpressionValidator.validate(expression, validationRegEx)
        );
        System.out.println(exception.getMessage());
        assertEquals("Illegal component after variable", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "a+",
                    "a(",
                    "a^",
                    "aaa",
            }
    )
    void validateInvalidComponentAfterVariableRegExTest(String expression) {
        ValidationRegEx validationRegEx = ValidationRegEx.INVALID_COMPONENT_AFTER_VARIABLE_REG_EX;

        assertTrue(ExpressionValidator.validate(expression, validationRegEx));
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "()",
                    "(45(11))",
                    "a(987(23))"
            }
    )
    void isValidNumberOfBracketsTest(String arg) {
        assertTrue(ExpressionValidator.isValidNumberOfBrackets(arg));
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "())",
                    "((45(11))",
                    "a(987(23)))"
            }
    )
    void isValidNumberOfBracketsExceptionTest(String arg) {
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ExpressionValidator.isValidNumberOfBrackets(arg));
        assertEquals("Illegal number of Brackets",exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "a",
                    "sin",
                    "cos",
                    "a+sin",
                    "a+sin(cos(30))",
            }
    )
    void containsValidLetterComponentsTest(String arg) {
        assertTrue(ExpressionValidator.containsValidLetterComponents(arg));
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "aa",
                    "aaa",
                    "cosa",
                    "bsin",
                    "a+sinn(cos(30))",
            }
    )
    void containsValidLetterComponentsExceptionTest(String arg) {
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ExpressionValidator.containsValidLetterComponents(arg));
        assertEquals("Invalid variable value string",exception.getMessage());
    }


}