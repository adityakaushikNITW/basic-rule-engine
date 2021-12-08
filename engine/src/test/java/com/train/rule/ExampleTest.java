package com.train.rule;

import com.train.rule.examples.TestClasses;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class ExampleTest {

    @Test
    public void spelContextTest() {
        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);
        TestClasses.Inventor tesla = new TestClasses.Inventor("Nikola Tesla", c.getTime(), "Serbian");
        TestClasses.ContextSample contextSample = new TestClasses.ContextSample();
        contextSample.getMyContext().put("name", tesla);
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("myContext.get('name').name");
        EvaluationContext context = new StandardEvaluationContext(contextSample);
        String name = (String) exp.getValue(context);
        System.out.println(name);
    }

    @Test
    public void spelContextTest_01() {
        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);
        TestClasses.Inventor tesla = new TestClasses.Inventor("Nikola Tesla", c.getTime(), "Serbian");
        TestClasses.ContextSample contextSample = new TestClasses.ContextSample();
        contextSample.getMyContext().put("name", tesla);
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("myContext['name'].name");
        EvaluationContext context = new StandardEvaluationContext(contextSample);
        String name = (String) exp.getValue(context);
        System.out.println(name);
    }

    @Test
    public void spelContextTest02() {
        TestClasses.ContextSample contextSample = new TestClasses.ContextSample();
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("new int[]{1,2,3}");
        StandardEvaluationContext context = new StandardEvaluationContext(contextSample);
        int[] name = (int[]) exp.getValue(context);
        System.out.println(Arrays.stream(name).max());
    }

    @Test
    public void spelContextTest03() {
        TestClasses.ContextSample contextSample = new TestClasses.ContextSample();
        contextSample.getMyContext().put("input", new HashMap<>(){{
            put("slabConfig", new HashMap<>(){{
                put("slabList", Arrays.asList(new HashMap(){{
                    put("min", 10);
                    put("max", 20);
                }}));
            }});
        }});
        ExpressionParser parser = new SpelExpressionParser();


        Expression exp = parser.parseExpression("{name:'Aditya',city:'Meerut','slabList':myContext['input']['slabConfig']['slabList']}");
        StandardEvaluationContext context = new StandardEvaluationContext(contextSample);
//        context.setBeanResolver(new MyBeanResolver());
        Map<String, Object> name = (Map<String, Object>) exp.getValue(context);
        contextSample.getMyContext().putAll(name);
        System.out.println(name);
        System.out.println(contextSample.getMyContext());
    }
}
