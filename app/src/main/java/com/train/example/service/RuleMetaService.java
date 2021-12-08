package com.train.example.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.train.example.utils.CommonUtils;
import com.train.rule.pojo.RuleNode;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RuleMetaService {

    private AmazonDynamoDB dynamoDB;

    public RuleNode saveRule(final RuleNode ruleNode) {
        PutItemRequest putItemRequest = new PutItemRequest();
        putItemRequest.setTableName("rules");
        putItemRequest.setItem(CommonUtils.getRuleNodeItemMap(ruleNode));
        PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
        return CommonUtils.getRuleNode(putItemResult.getAttributes());
    }

    public RuleNode getRule(final String ruleId) {
        GetItemRequest getItemRequest = new GetItemRequest();
        getItemRequest.setTableName("rules");
        getItemRequest.setKey(new HashMap<>()
        {{
            put("One", new AttributeValue().withS(ruleId));
        }});
        GetItemResult getItemResult = dynamoDB.getItem(getItemRequest);
        return CommonUtils.getRuleNode(getItemResult.getItem());
    }
}
