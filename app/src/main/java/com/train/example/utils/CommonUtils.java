package com.train.example.utils;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.train.example.dao.RuleNodeDAO;
import com.train.rule.enums.RuleType;
import com.train.rule.pojo.RuleNode;

import java.util.*;
import java.util.stream.Collectors;

import static com.train.rule.pojo.RuleNode.*;

public class CommonUtils {

    public static RuleNode getRuleNode(final Map<String, AttributeValue> itemMap) {
        RuleNode ruleNode = new RuleNode();
        ruleNode.setRuleId(itemMap.get(RULE_ID).getS());
        ruleNode.setRuleType(RuleType.valueOf(itemMap.get(RULE_TYPE).getS()));
        ruleNode.setExpression(itemMap.get(EXPRESSION).getS());
        ruleNode.setOnFalse(getRuleNodeList(itemMap.get(ON_FALSE).getL()));
        ruleNode.setOnTrue(getRuleNodeList(itemMap.get(ON_TRUE).getL()));
        ruleNode.setOutputVariable(itemMap.get(OUTPUT_VARIABLE).getS());

        return ruleNode;
    }

    public static List<RuleNode> getRuleNodeList(List<AttributeValue> attributeValueList) {
        return attributeValueList.stream()
                .map(x -> getRuleNode(x.getM()))
                .collect(Collectors.toList());
    }

    public static RuleNode getRuleNode(final RuleNodeDAO ruleNodeDAO) {
        if(Objects.isNull(ruleNodeDAO)) {
            return null;
        }
        return RuleNode.builder().
                ruleId(ruleNodeDAO.getRuleId()).
                ruleType(ruleNodeDAO.getRuleType()).
                expression(ruleNodeDAO.getExpression()).
                onFalse(ruleNodeDAO.getOnFalse().stream().map(CommonUtils::getRuleNode).collect(Collectors.toList())).
                onTrue(ruleNodeDAO.getOnTrue().stream().map(CommonUtils::getRuleNode).collect(Collectors.toList())).
                outputVariable(ruleNodeDAO.getOutputVariable()).build();
    }

    public static RuleNodeDAO getRuleNodeDAO(final RuleNode ruleNode) {
        if(Objects.isNull(ruleNode)) {
            return null;
        }
        return RuleNodeDAO.builder().
                ruleId(ruleNode.getRuleId()).
                ruleType(ruleNode.getRuleType()).
                expression(ruleNode.getExpression()).
                onFalse(ruleNode.getOnFalse().stream().map(CommonUtils::getRuleNodeDAO).collect(Collectors.toList())).
                onTrue(ruleNode.getOnTrue().stream().map(CommonUtils::getRuleNodeDAO).collect(Collectors.toList())).
                outputVariable(ruleNode.getOutputVariable()).
                lastUpdatedAtEpoch(new Date().getTime()).build();

    }

    public static Map<String, AttributeValue> getRuleNodeItemMap(final RuleNode ruleNode) {
        Map<String, AttributeValue> itemMap = new HashMap<>();
        itemMap.put(RULE_ID, new AttributeValue().withS(ruleNode.getRuleId()));
        itemMap.put(RULE_TYPE, new AttributeValue().withS(ruleNode.getRuleType().name()));
        itemMap.put(EXPRESSION, new AttributeValue().withS(ruleNode.getExpression()));
        itemMap.put(ON_FALSE, new AttributeValue().withL(getAttributeValueList(ruleNode.getOnFalse())));
        itemMap.put(ON_TRUE, new AttributeValue().withL(getAttributeValueList(ruleNode.getOnTrue())));
        itemMap.put(OUTPUT_VARIABLE, new AttributeValue().withS(ruleNode.getOutputVariable()));

        return itemMap;
    }

    public static List<AttributeValue> getAttributeValueList(List<RuleNode> ruleNodes) {
        return ruleNodes.stream().map(CommonUtils::getRuleNodeItemMap)
                .map(x -> new AttributeValue().withM(x))
                .collect(Collectors.toList());
    }
}
