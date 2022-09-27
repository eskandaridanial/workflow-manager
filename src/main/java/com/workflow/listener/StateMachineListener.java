package com.workflow.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StateMachineListener extends StateMachineListenerAdapter<String , String> {

    @Override
    public void stateChanged(State<String, String> from, State<String, String> to) {
        log.info("state changed from : " + from + " to : " + to);
    }

}
