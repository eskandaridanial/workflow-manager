package com.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryTransactionRequestDto {
    private String inquiry;
    private String transaction;
    private HashMap<String , Object> inquiryParams;
    private HashMap<String , Object> transactionParams;
}
