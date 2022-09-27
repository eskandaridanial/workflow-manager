package com.workflow.controller;

import com.workflow.model.InquiryTransactionRequestDto;
import com.workflow.model.InquiryTransactionResponseDto;
import com.workflow.service.StateMachineService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class StateMachineController {

    private final StateMachineService stateMachineService;

    @PostMapping(value = "/inquiry-transaction")
    public ResponseEntity<InquiryTransactionResponseDto> inquiryTransaction(@RequestBody InquiryTransactionRequestDto info) {
        return ResponseEntity.ok(stateMachineService.inquiryTransaction(info.getInquiry() , info.getTransaction() , info.getInquiryParams() ,info.getTransactionParams()));
    }
}
