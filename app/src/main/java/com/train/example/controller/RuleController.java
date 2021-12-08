package com.train.example.controller;

import com.train.example.service.RuleMetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.train.rule.pojo.RuleNode;

@RestController("/api/demo/v1/rules")
public class RuleController {

    RuleMetaService ruleMetaService;

    public RuleController(RuleMetaService ruleMetaService) {
        this.ruleMetaService = ruleMetaService;
    }

    @PostMapping("/insert")
    public ResponseEntity<RuleNode> saveRule(@RequestBody RuleNode ruleNode) {
        return ResponseEntity.ok(ruleMetaService.saveRule(ruleNode));
    }

    @GetMapping("/get")
    public ResponseEntity<RuleNode> getRule(@RequestParam String ruleName) {
        return ResponseEntity.ok(ruleMetaService.getRule(ruleName));
    }
}
