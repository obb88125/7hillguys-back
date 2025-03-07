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

    @PostMapping("/paymentRequest")
    public PaymentResponse processPayment(@RequestBody PosRequest request) {
        return paymentService.processPayment(request);
    }

//    // 포스기에서 전달받은 환불 요청
//    @PostMapping("/refund")
//    public RefundResponse processRefund(@RequestBody PosRequest request) {
//        paymentService.processRefund(request);
//    }

}
