package com.shpp.p2p.cs.ohololobov.assignment9;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ValidationRegExTest {

    static Stream<Arguments> valuesForValidationRegEx() {
        return Stream.of(
                Arguments.of(ValidationRegEx.VALID_STRUCTURES_REG_EX,
                        "^((\\d+(\\.?\\d+)?)+|[a-z]|[()+*/^-])+$",
                        "Illegal component is present",
                        true
                ),
                Arguments.of(ValidationRegEx.VALID_BEGINNING_REG_EX,
                        "^(-?[a-z])|^(-?(\\d+(\\.?\\d+)?))|^(-?[(])",
                        "Illegal structure at start of expression",
                        true
                ),
                Arguments.of(ValidationRegEx.INVALID_END_REG_EX,
                        "([.(+*/^-]|[^a-z][a-z]{2})$",
                        "Illegal structure at end of expression",
                        false
                ),
                Arguments.of(ValidationRegEx.INVALID_COMPONENT_AFTER_MATH_FUNCTION_REG_EX,
                        "(sin|cos|tan|ctan|atan|actan|asin|sqrt)[^(]",
                        "Illegal component after function",
                        false
                ),
                Arguments.of(ValidationRegEx.INVALID_BEGINNING_REG_EX,
                        "^[^0-9a-z-]|^(-[^0-9a-z])",
                        "Illegal structure at start of expression",
                        false
                ),
                Arguments.of(ValidationRegEx.INVALID_DECIMAL_SEPARATOR_USING_REG_EX,
                        "\\.\\D|\\D\\.|\\d\\.\\D|\\D\\.\\d",
                        "Illegal decimal separator using",
                        false
                ),
                Arguments.of(ValidationRegEx.INVALID_COMPONENT_AFTER_OPENING_BRACKET_REG_EX,
                        "[(][^0-9a-z-]",
                        "Illegal component after opening bracket",
                        false
                ),
                Arguments.of(ValidationRegEx.INVALID_COMPONENT_AFTER_VARIABLE_REG_EX,
                        "([a-z](\\d|\\.))|[a-z]{2}[^a-z]",
                        "Illegal component after variable",
                        false
                ),
                Arguments.of(ValidationRegEx.INVALID_COMPONENT_AFTER_OPERATOR_REG_EX,
                        "[+*/^-][\\.|)|+*/^-]",
                        "Illegal component after operator",
                        false
                )
        );
    }

    @ParameterizedTest
    @MethodSource("valuesForValidationRegEx")
    void patternTest(ValidationRegEx validationRegEx, String pattern, String exceptionMSG, boolean isPositiveExpectation) {
        assertEquals(pattern,validationRegEx.pattern());
    }

    @ParameterizedTest
    @MethodSource("valuesForValidationRegEx")
    void exceptionMSGTest(ValidationRegEx validationRegEx, String pattern, String exceptionMSG, boolean isPositiveExpectation) {
        assertEquals(exceptionMSG,validationRegEx.exceptionMSG());
    }

    @ParameterizedTest
    @MethodSource("valuesForValidationRegEx")
    void isPositiveExpectation(ValidationRegEx validationRegEx, String pattern, String exceptionMSG, boolean isPositiveExpectation) {
        assertEquals(isPositiveExpectation,validationRegEx.isPositiveExpectation());
    }

    @Test
    void letterRegEx() {
        assertEquals("a-z", ValidationRegEx.letterRegEx());
    }
}