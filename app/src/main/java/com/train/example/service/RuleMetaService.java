package com.train.example.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.train.example.dao.RuleNodeDAO;
import com.train.example.utils.CommonUtils;
import com.train.rule.pojo.RuleNode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RuleMetaService {

    private final MongoTemplate mongoTemplate;

    public RuleMetaService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public RuleNode saveRule(final RuleNode ruleNode) {
        RuleNodeDAO nodeDAO = mongoTemplate.save(CommonUtils.getRuleNodeDAO(ruleNode));
        return CommonUtils.getRuleNode(nodeDAO);
    }

    public RuleNode getRule(final String ruleId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(RuleNode.RULE_ID).is(ruleId));

        List<RuleNodeDAO> nodeDAOList = mongoTemplate.find(query, RuleNodeDAO.class);
        return CommonUtils.getRuleNode(nodeDAOList.get(0));
    }
}
