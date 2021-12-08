package com.train.example.pojo;

import com.train.rule.pojo.RuleNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleEvalWithInputDTO {
    RuleNode ruleNode;
    Map<String, Object> input;
}
