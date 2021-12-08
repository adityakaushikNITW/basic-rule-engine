package com.train.example.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Qualifier("ruleUtils")
public class RuleUtils {

    public Double calculateDistance(Double point1, Double point2) {
        return Math.sqrt((point1 - point2) * (point1 - point2));
    }

    public void pushMessageToKafka() {

    }
}
