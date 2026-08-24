package com.shpp.p2p.cs.ohololobov.assignment9;

public enum ValidationRegEx {
    VALID_STRUCTURES_REG_EX(RegEx.VALID_STRUCTURES_REG_EX, "Illegal component is present", true),
    VALID_BEGINNING_REG_EX(RegEx.VALID_BEGINNING_OF_EXPRESSION_REG_EX, "Illegal structure at start of expression", true),
    INVALID_END_REG_EX(RegEx.INVALID_END_OF_EXPRESSION_REG_EX, "Illegal structure at end of expression", false),
    INVALID_COMPONENT_AFTER_MATH_FUNCTION_REG_EX(RegEx.INVALID_COMPONENT_AFTER_MATH_FUNCTION_REG_EX, "Illegal component after function", false),
    INVALID_BEGINNING_REG_EX(RegEx.INVALID_BEGINNING_OF_EXPRESSION_SEQUENCES_REG_EX, "Illegal structure at start of expression", false),
    INVALID_DECIMAL_SEPARATOR_USING_REG_EX(RegEx.INVALID_DECIMAL_SEPARATOR_USING_REG_EX, "Illegal decimal separator using", false),
    INVALID_COMPONENT_AFTER_OPENING_BRACKET_REG_EX(RegEx.INVALID_COMPONENT_AFTER_OPENING_BRACKET_REG_EX, "Illegal component after opening bracket", false),
    INVALID_COMPONENT_AFTER_OPERATOR_REG_EX(RegEx.INVALID_COMPONENT_AFTER_OPERATOR_REG_EX, "Illegal component after operator", false),
    INVALID_COMPONENT_AFTER_VARIABLE_REG_EX(RegEx.INVALID_COMPONENT_AFTER_VARIABLE_REG_EX, "Illegal component after variable", false);
    private final String pattern;
    private final String exceptionMSG;
    private final boolean isPositiveExpectation;

    ValidationRegEx(String pattern, String exceptionMSG, boolean isPositiveExpectation) {
        this.pattern = pattern;
        this.exceptionMSG = exceptionMSG;
        this.isPositiveExpectation = isPositiveExpectation;
    }

    public String pattern() {
        return pattern;
    }

    public String exceptionMSG() {
        return exceptionMSG;
    }

    public boolean isPositiveExpectation() {
        return isPositiveExpectation;
    }

    private static class RegEx {
        private static final String DECIMAL_SEPARATOR = ".";


        private static final String MINUS_SIGN = Operator.SUBTRACTION.getValue();

        private static final String ALL_ARITHMETIC_OPERATORS = Operator.operatorsToString(true);
        private static final String LETTERS_REG_EX = "a-z";
        private static final String OPENING_BRACKET = Bracket.OPENING_BRACKET.getValue();
        private static final String CLOSING_BRACKET = Bracket.CLOSING_BRACKET.getValue();
        private static final String BRACKETS = Bracket.bracketToString();


        private static final String VALID_STRUCTURES_REG_EX = "^((\\d+(\\" + DECIMAL_SEPARATOR + "?\\d+)?)+" +
                "|[" + LETTERS_REG_EX + "]|[" + BRACKETS + ALL_ARITHMETIC_OPERATORS + "])+$";

        private static final String VALID_BEGINNING_OF_EXPRESSION_REG_EX = "^(" + MINUS_SIGN + "?[" + LETTERS_REG_EX + "])|^(" + MINUS_SIGN + "?(\\d+(\\" + DECIMAL_SEPARATOR + "?\\d+)?))|^(" + MINUS_SIGN + "?[" + OPENING_BRACKET + "])";
        private static final String INVALID_END_OF_EXPRESSION_REG_EX = "([" + DECIMAL_SEPARATOR + OPENING_BRACKET + ALL_ARITHMETIC_OPERATORS + "]|[^" + LETTERS_REG_EX + "][" + LETTERS_REG_EX + "]{2})$";
        private static final String INVALID_COMPONENT_AFTER_MATH_FUNCTION_REG_EX = MathFunction.mathFunctionsToStringWithOrSeparatorRegEx() + "[^" + OPENING_BRACKET + "]";
        private static final String INVALID_BEGINNING_OF_EXPRESSION_SEQUENCES_REG_EX = "^[^0-9" + LETTERS_REG_EX + MINUS_SIGN + "]|^(" + MINUS_SIGN + "[^0-9" + LETTERS_REG_EX + "])";
        private static final String INVALID_DECIMAL_SEPARATOR_USING_REG_EX = "\\" + DECIMAL_SEPARATOR + "\\D|\\D\\" + DECIMAL_SEPARATOR + "|\\d\\" + DECIMAL_SEPARATOR + "\\D|\\D\\" + DECIMAL_SEPARATOR + "\\d";
        private static final String INVALID_COMPONENT_AFTER_OPENING_BRACKET_REG_EX = "[(][^0-9" + LETTERS_REG_EX + MINUS_SIGN + "]";
        private static final String INVALID_COMPONENT_AFTER_VARIABLE_REG_EX = "([" + LETTERS_REG_EX + "](\\d|\\" + DECIMAL_SEPARATOR + "))|["+LETTERS_REG_EX+"]{2}[^"+LETTERS_REG_EX+"]";
        private static final String INVALID_COMPONENT_AFTER_OPERATOR_REG_EX = "[" + ALL_ARITHMETIC_OPERATORS + "][\\" + DECIMAL_SEPARATOR + "|" + CLOSING_BRACKET + "|" + ALL_ARITHMETIC_OPERATORS + "]";
    }
    public static String getLetterRegEx(){
        return RegEx.LETTERS_REG_EX;
    }

}
