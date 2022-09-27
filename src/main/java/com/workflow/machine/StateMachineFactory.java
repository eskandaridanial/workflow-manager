package com.workflow.machine;

import com.workflow.listener.StateMachineListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.workflow.action.StateMachineAction.*;

@Slf4j
@Component
@AllArgsConstructor
public class StateMachineFactory {

    private static final String START_STATE = "start";
    private static final String START_EVENT = "start_event";
    private static final String INQUIRY_STATE = "inquiry";
    private static final String INQUIRY_EVENT = "inquiry_event";
    private static final String TRANSACTION_STATE = "transaction";
    private static final String TRANSACTION_EVENT = "transaction_event";
    private static final String END_STATE = "end_state";

    private final StateMachineListener listener;

    @Bean(value = "inquiryTransaction")
    @Scope(value = "request", proxyMode= ScopedProxyMode.TARGET_CLASS)
    public StateMachine<String , String> inquiryTransaction() throws Exception {
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
        UUID id = UUID.randomUUID();
        builder.configureConfiguration()
                .withConfiguration()
                .autoStartup(true)
//                .listener(listener)
                .machineId(id.toString());

        builder.configureStates()
                .withStates()
                .initial(START_STATE)
                .state(INQUIRY_STATE)
                .state(TRANSACTION_STATE)
                .end(END_STATE);

        builder.configureTransitions()
                .withExternal()
                .source(START_STATE).target(INQUIRY_STATE)
                .event(START_EVENT)
                .and()
                .withExternal()
                .source(INQUIRY_STATE).target(TRANSACTION_STATE)
                .event(INQUIRY_EVENT).action(inquiryAction())
                .and()
                .withExternal()
                .source(TRANSACTION_STATE).target(END_STATE)
                .event(TRANSACTION_EVENT).action(transactionAction());

        StateMachine<String , String> stateMachine = builder.build();

        log.info("state machine built and configured successfully with id " + id.toString());

        return stateMachine;
    }
}