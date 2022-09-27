package com.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InquiryTransactionResponseDto {
    private String inquiryState;
    private String transactionState;
    private Map<String ,Object> inquiryResult;
    private Map<String ,Object> transactionResult;
}
