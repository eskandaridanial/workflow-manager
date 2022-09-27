package com.workflow.service;

import com.workflow.model.InquiryTransactionResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class StateMachineService {

    private static final String START_EVENT = "start_event";
    private static final String INQUIRY_EVENT = "inquiry_event";
    private static final String TRANSACTION_EVENT = "transaction_event";

    @Qualifier(value = "inquiryTransaction")
    private final StateMachine<String , String> inquiryTransaction;

    public InquiryTransactionResponseDto inquiryTransaction(String inquiry , String transaction
            , Map<String , Object> inquiryParams , Map<String , Object> transactionParams){

        Map<Object , Object> vars = inquiryTransaction.getExtendedState().getVariables();
        vars.put("inquiry" , inquiry);
        vars.put("transaction" , transaction);
        vars.put("inquiryParams" , inquiryParams);
        vars.put("transactionParams" , transactionParams);

        inquiryTransaction.sendEvent(Mono.just(MessageBuilder.withPayload(START_EVENT).build())).blockFirst();
        inquiryTransaction.sendEvent(Mono.just(MessageBuilder.withPayload(INQUIRY_EVENT).build())).blockFirst();
        inquiryTransaction.sendEvent(Mono.just(MessageBuilder.withPayload(TRANSACTION_EVENT).build())).blockFirst();


        Map<String, Object> inquiryResult = (Map<String, Object>) vars.get("inquiryResult");
        Map<String, Object> transactionResult = (Map<String, Object>) vars.get("transactionResult");
        Map<String, Object> inq = (Map<String, Object>) inquiryResult.get("result");
        Map<String, Object> tran = (Map<String, Object>) transactionResult.get("result");

        InquiryTransactionResponseDto response = new InquiryTransactionResponseDto();
        if ((int) inq.get("code") == 0)
            response.setInquiryState("SUCCESSFUL");
        else
            response.setInquiryState("FAILED");
        response.setInquiryResult((Map<String, Object>) vars.get("inquiryResult"));

        if ((int) tran.get("code") == 0)
            response.setTransactionState("SUCCESSFUL");
        else
            response.setTransactionState("FAILED");
        response.setTransactionResult((Map<String, Object>) vars.get("transactionResult"));

        return response;
    }
}
