package com.baeldung.spring.statemachine;

import com.baeldung.spring.statemachine.config.StateMachineWithChoiceConfiguration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StateMachineWithChoiceConfiguration.class)
public class StateMachineWithChoiceIntegrationTest {

    @Autowired
    private StateMachine<String, String> stateMachine;

    @BeforeEach
    public void setUp() {
        stateMachine.startReactively().block();
    }

    @DisplayName("E state")
    @Test
    public void whenSimpleStringStateMachineEvents_stateE() {
        assertEquals("A", stateMachine.getState().getId());

        stateMachine.sendEvent("EVENT_AB");
        assertEquals("B", stateMachine.getState().getId());

        stateMachine.sendEvent("EVENT_BC");
        assertEquals("C", stateMachine.getState().getId());

        stateMachine.sendEvent("EVENT_CE");
        assertEquals("E", stateMachine.getState().getId());
    }

    @Disabled
    @DisplayName("F state")
    @Test
    public void whenSimpleStringStateMachineEvents_stateF() {
        assertEquals("A", stateMachine.getState().getId());

        stateMachine.sendEvent("EVENT_AB");
        assertEquals("B", stateMachine.getState().getId());

        stateMachine.sendEvent("EVENT_BC");
        assertEquals("C", stateMachine.getState().getId());

        stateMachine.sendEvent("EVENT_CF");
        assertEquals("F", stateMachine.getState().getId());
    }

    @Disabled
    @DisplayName("G state")
    @Test
    public void whenSimpleStringStateMachineEvents_stateG() {
        assertEquals("A", stateMachine.getState().getId());

        stateMachine.sendEvent("EVENT_AB");
        assertEquals("B", stateMachine.getState().getId());

        stateMachine.sendEvent("EVENT_BC");
        assertEquals("C", stateMachine.getState().getId());

        stateMachine.sendEvent("EVENT_CG");
        assertEquals("G", stateMachine.getState().getId());
    }

    @Disabled
    @DisplayName("D state")
    @Test
    public void whenSimpleStringStateMachineEvents_stateD() {
        assertEquals("A", stateMachine.getState().getId());

        stateMachine.sendEvent("EVENT_AB");
        assertEquals("B", stateMachine.getState().getId());

        stateMachine.sendEvent("EVENT_BD");
        assertEquals("D", stateMachine.getState().getId());
    }

    @AfterEach
    public void tearDown() {
        stateMachine.stopReactively().block();
        stateMachine.startReactively().block();
    }
}

