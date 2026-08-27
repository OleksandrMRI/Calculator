package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.List;

/**
 * This record contains linear name of variable
 * and result of parsing value of variable
 * as list of tokens in reverse Polish notation
 * @param variableName string value of variable name
 * @param parsedVariableValue list of string  tokens as result of parsing in reverse Polish notation
 */
public record ParsedVariableRecord(String variableName, List<String> parsedVariableValue) {
}
