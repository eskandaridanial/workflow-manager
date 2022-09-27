package com.workflow.action;

import com.workflow.util.HttpUtil;
import com.workflow.util.ServiceReader;
import lombok.AllArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class StateMachineAction implements Action<String, String> {

    public static Action<String, String> inquiryAction() {
        return context -> {
            String serviceName = (String) context.getExtendedState().getVariables().get("inquiry");
            Map<String , Object> inquiryParams = (Map<String, Object>) context.getExtendedState().getVariables().get("inquiryParams");
            Map<String , Object> inquiryResult = HttpUtil.post(inquiryParams , ServiceReader.serviceUrl(serviceName));
            context.getExtendedState().getVariables().put("inquiryResult" , inquiryResult);
        };
    }

    public static Action<String, String> transactionAction() {
        return context -> {

            String serviceName = (String) context.getExtendedState().getVariables().get("transaction");
            Map<String , Object> inquiryResult = (Map<String, Object>) context.getExtendedState().getVariables().get("inquiryResult");
            Map<String , Object> transactionParams = (Map<String, Object>) context.getExtendedState().getVariables().get("transactionParams");
            transactionParams = HttpUtil.matchCommonParams(inquiryResult, transactionParams);
            Map<String , Object> transactionResult = HttpUtil.post(transactionParams , ServiceReader.serviceUrl(serviceName));
            context.getExtendedState().getVariables().put("transactionResult" , transactionResult);
        };
    }

    @Override
    public void execute(StateContext<String, String> context) { }
}
