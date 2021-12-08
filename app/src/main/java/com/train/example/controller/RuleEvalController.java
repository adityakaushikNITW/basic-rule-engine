package com.train.example.controller;

import com.train.example.pojo.RuleEvalWithInputDTO;
import com.train.example.service.RuleMetaService;
import com.train.rule.pojo.RuleContext;
import com.train.rule.pojo.RuleNode;
import com.train.rule.service.RuleEvalService;
import org.springframework.expression.BeanResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/demo/v1/rule_executions")
public class RuleEvalController {

    BeanResolver beanResolver;

    RuleEvalService ruleEvalService;

    public RuleEvalController(BeanResolver beanResolver) {
        this.beanResolver = beanResolver;
        this.ruleEvalService = new RuleEvalService(beanResolver);
    }

    @PostMapping
    public ResponseEntity<Object> evalRule(@RequestBody RuleNode ruleNode) {
        RuleContext ruleContext = new RuleContext();
        ruleEvalService.evaluateRule(ruleNode, ruleContext);
        Object output = ruleContext.getMetadata().get("output_key");
        return ResponseEntity.ok(output);
    }

    @PostMapping("/with-input")
    public ResponseEntity<Object> evalRuleWithInput(@RequestBody RuleEvalWithInputDTO ruleNode) {
        RuleContext ruleContext = new RuleContext();
        ruleContext.getMetadata().putAll(ruleNode.getInput());
        ruleEvalService.evaluateRule(ruleNode.getRuleNode(), ruleContext);
        Object output = ruleContext.getMetadata().get("output_key");
        return ResponseEntity.ok(output);
    }
}
