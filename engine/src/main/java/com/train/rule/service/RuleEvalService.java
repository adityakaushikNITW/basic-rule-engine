package com.train.rule.service;

import com.train.rule.pojo.RuleNode;
import com.train.rule.pojo.RuleContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Slf4j
@Service
public class RuleEvalService {

    private final BeanResolver beanResolver;

    public RuleEvalService(BeanResolver beanResolver) {
        this.beanResolver = beanResolver;
    }

    public static final String NULL_STRING_CONSTANT = "NULL";

    public void evaluateRule(final RuleNode input, final RuleContext ruleContext) {
        StandardEvaluationContext context = new StandardEvaluationContext(ruleContext);
        context.setBeanResolver(beanResolver);
        switch (input.getRuleType()) {
            case START -> {
                assert CollectionUtils.isEmpty(input.getOnFalse());

                onStartState(input, ruleContext);
            }
            case ACTION -> {
                assert ObjectUtils.isEmpty(input.getOutputVariable());

                onActionState(input, ruleContext, context);
            }
            case CONDITION -> {
                onConditionState(input, ruleContext, context);
            }
            case CONFIG -> {
                onConfigState(input, ruleContext, context);
            }
            case END -> {}
            default -> {}
        }
    }

    private void onStartState(RuleNode input, RuleContext ruleContext) {
        for(RuleNode ruleNode : input.getOnTrue()) {
            evaluateRule(ruleNode, ruleContext);
        }
    }

    private void onActionState(RuleNode input, RuleContext ruleContext, StandardEvaluationContext evaluationContext) {
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression(input.getExpression());
        Object result = expression.getValue(evaluationContext);
        if(ObjectUtils.isEmpty(result)) {
            result = NULL_STRING_CONSTANT;
        }
        ruleContext.getMetadata().put(String.valueOf(input.getOutputVariable()), result);
    }

    private void onConfigState(RuleNode input, RuleContext ruleContext, StandardEvaluationContext evaluationContext) {
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression(input.getExpression());
        Map<String, Object> configMap = (Map<String, Object>) expression.getValue(evaluationContext);

        ruleContext.getMetadata().put(input.getOutputVariable(), configMap);
        input.getOnTrue().forEach(ruleNode -> evaluateRule(ruleNode, ruleContext));
    }

    private void onConditionState(RuleNode input, RuleContext ruleContext, StandardEvaluationContext evaluationContext) {
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression(input.getExpression());
        Boolean result = (Boolean) expression.getValue(evaluationContext);
        if(!ObjectUtils.isEmpty(result)) {
            if (result) {
                input.getOnTrue().forEach(ruleNode -> evaluateRule(ruleNode, ruleContext));
            } else {
                input.getOnFalse().forEach(ruleNode -> evaluateRule(ruleNode, ruleContext));
            }
        } else {
            log.error("");
        }
    }
}
