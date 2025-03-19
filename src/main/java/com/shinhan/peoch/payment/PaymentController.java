package com.shinhan.peoch.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // 결제 요청
    @PostMapping("/paymentRequest")
    public PosResponse processPayment(@RequestBody PosPaymentRequest request) {
        return paymentService.processPayment(request);
    }

    // 환불 요청
    @PostMapping("/refundRequest")
    public PosResponse processRefund(@RequestBody PosRefundRequest request) {
        return paymentService.processRefund(request);
    }

    // 결제 요청 더미 데이터
    @PostMapping("/bulkPaymentRequest")
    public ResponseEntity<List<PosResponse>> processBulkPayment(@RequestBody List<PosPaymentRequest> requests) {
        List<PosResponse> responses = requests.stream()
                .map(paymentService::processPayment) // 단건 결제 요청을 반복 처리
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

}
