package com.shpp.p2p.cs.ohololobov.assignment9;

public class GraphVariableParser {
    double getValue(VariableEqualityRecord variableEqualityRecord){
        return Double.parseDouble(variableEqualityRecord.variableValue());
    }
}
