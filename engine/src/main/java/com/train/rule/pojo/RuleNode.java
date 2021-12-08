package com.train.rule.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.train.rule.enums.RuleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RuleNode {

    public static final String RULE_ID = "ruleId";
    public static final String RULE_TYPE = "ruleType";
    public static final String EXPRESSION = "expression";
    public static final String OUTPUT_VARIABLE = "outputVariable";
    public static final String ON_TRUE = "onTrue";
    public static final String ON_FALSE = "onFalse";

    @JsonProperty(RULE_ID)
    String ruleId;

    @JsonProperty(RULE_TYPE)
    RuleType ruleType;

    @JsonProperty(EXPRESSION)
    String expression;

    @JsonProperty(OUTPUT_VARIABLE)
    String outputVariable;

    @JsonProperty(ON_TRUE)
    List<RuleNode> onTrue;

    @JsonProperty(ON_FALSE)
    List<RuleNode> onFalse;
}
