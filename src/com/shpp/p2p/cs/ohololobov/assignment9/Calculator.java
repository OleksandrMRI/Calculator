package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.List;
import java.util.Map;

public interface Calculator {
double calculate(List<String> expressionTokens,
                 Map<String, List<String>> variableValueTokens);
}
