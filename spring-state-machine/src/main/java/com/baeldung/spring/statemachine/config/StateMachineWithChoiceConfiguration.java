package com.baeldung.spring.statemachine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Logger;

@Configuration
@EnableStateMachine
public class StateMachineWithChoiceConfiguration extends StateMachineConfigurerAdapter<String, String> {

    private static final Logger LOGGER = Logger.getLogger(StateMachineWithChoiceConfiguration.class.getName());

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new StateMachineListener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states
                .withStates()
                .initial("A")
                .state("B")
                .state("C")
                .end("E")
                .end("D")
                .end("F")
                .end("G");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions
                .withExternal()
                .source("A")
                .target("B")
                .event("EVENT_AB")
                .and()

                .withExternal()
                .source("B")
                .target("C")
                .event("EVENT_BC")
                .action(entryBC())

                .and()
                .withExternal()
                .source("B")
                .target("D")
                .event("EVENT_BD")
                .action(entryBD())
                .and()

                .withExternal()
                .source("C")
                .target("E")
                .event("EVENT_CE")
                .and()

                .withExternal()
                .source("C")
                .target("F")
                .event("EVENT_CF")
                .and()

                .withExternal()
                .source("C")
                .target("G")
                .event("EVENT_CG")
                .and()

                .withExternal()
                .source("G")
                .target("A")
                .event("EVENT_GA")
                .and()

                .withExternal()
                .source("E")
                .target("A")
                .event("EVENT_EA")
                .and()

                .withExternal()
                .source("F")
                .target("A")
                .event("EVENT_FA")
                .and()

                .withExternal()
                .source("D")
                .target("none")
                .event("EVENT_DA")
                .and()

        ;
    }

    @Bean
    public Guard<String, String> simpleGuard() {
        return ctx -> {
            int approvalCount = (int) ctx
                    .getExtendedState()
                    .getVariables()
                    .getOrDefault("approvalCount", 0);
            return approvalCount > 0;
        };
    }

    @Bean
    public Action<String, String> entryBC() {
        return ctx -> LOGGER.info("Entry  BC " + ctx
                .getTarget()
                .getId());
    }

    @Bean
    public Action<String, String> entryBD() {
        return ctx -> LOGGER.info("Entry  BD " + ctx
                .getTarget()
                .getId());
    }

    @Bean
    public Action<String, String> doAction() {
        return ctx -> LOGGER.info("Do " + ctx
                .getTarget()
                .getId());
    }

    @Bean
    public Action<String, String> executeAction() {
        return ctx -> {
            LOGGER.info("Execute " + ctx
                    .getTarget()
                    .getId());
            int approvals = (int) ctx
                    .getExtendedState()
                    .getVariables()
                    .getOrDefault("approvalCount", 0);
            approvals++;
            ctx
                    .getExtendedState()
                    .getVariables()
                    .put("approvalCount", approvals);
        };
    }

    @Bean
    public Action<String, String> exitAction() {
        return ctx -> LOGGER.info("Exit " + ctx
                .getSource()
                .getId() + " -> " + ctx
                .getTarget()
                .getId());
    }

    @Bean
    public Action<String, String> errorAction() {
        return ctx -> LOGGER.info("Error " + ctx
                .getSource()
                .getId() + ctx.getException());
    }

    @Bean
    public Action<String, String> initAction() {
        return ctx -> LOGGER.info(ctx
                .getTarget()
                .getId());
    }
}
