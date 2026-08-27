package com.shpp.p2p.cs.ohololobov.assignment9;

/**
 * Record with fields that represent base information about input vatiable equality
 * @param variableName variable name
 * @param variableValue second element of array from intup variable equality,
 *                      after splitting with "=" as separator
 * @param unaryMinus boolean is true when in input variable equality war "-" before variable name
 * @param sourceVariableEquality formated source string of variable equality without spaces in lowercase and with
 */
public record VariableEqualityRecord(String variableName, String variableValue, boolean unaryMinus, String sourceVariableEquality) {

}
