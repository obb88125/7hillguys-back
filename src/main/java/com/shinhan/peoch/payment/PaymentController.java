package com.shinhan.peoch.payment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
