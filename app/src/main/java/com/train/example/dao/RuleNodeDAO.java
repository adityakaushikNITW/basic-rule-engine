package com.train.example.dao;

import com.train.rule.enums.RuleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.util.List;

import static com.train.example.dao.RuleNodeDAO.RULE_NODE_TABLE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = RULE_NODE_TABLE)
public class RuleNodeDAO {

    public static final String RULE_NODE_TABLE = "ruleNodes";
    public static final String RULE_ID = "ruleId";
    public static final String RULE_TYPE = "ruleType";
    public static final String EXPRESSION = "expression";
    public static final String OUTPUT_VARIABLE = "outputVariable";
    public static final String ON_TRUE = "onTrue";
    public static final String ON_FALSE = "onFalse";
    public static final String LAST_UPDATED_AT = "last_updated_at";

    @Column(name = RULE_ID)
    String ruleId;

    @Column(name = RULE_TYPE)
    RuleType ruleType;

    @Column(name = EXPRESSION)
    String expression;

    @Column(name = OUTPUT_VARIABLE)
    String outputVariable;

    @Column(name = ON_TRUE)
    List<RuleNodeDAO> onTrue;

    @Column(name = ON_FALSE)
    List<RuleNodeDAO> onFalse;

    @Column(name = LAST_UPDATED_AT)
    Long lastUpdatedAtEpoch;
}
