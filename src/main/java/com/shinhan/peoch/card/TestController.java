package com.shinhan.peoch.card;

import com.shinhan.entity.*;
import com.shinhan.peoch.auth.entity.UserEntity;
import com.shinhan.peoch.payment.PaymentService;
import com.shinhan.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final StoreRepository storeRepository;
    private final BenefitRepository benefitRepository;
    private final MyBenefitRepository myBenefitRepository;

    public TestController(CardRepository cardRepository, UserRepository userRepository, PaymentService paymentService,
                          StoreRepository storeRepository, BenefitRepository benefitRepository,
                          MyBenefitRepository myBenefitRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
        this.storeRepository = storeRepository;
        this.benefitRepository = benefitRepository;
        this.myBenefitRepository = myBenefitRepository;
    }

    @PostMapping("/cardUserInsert")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/cardInsert")
    public ResponseEntity<CardEntity> createCard(@RequestBody CardEntity card) {
        CardEntity savedCard = cardRepository.save(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
    }

    @PostMapping("/storeInsert")
    public ResponseEntity<StoreEntity> createStore(@RequestBody StoreEntity store) {
        StoreEntity savedStore = storeRepository.save(store);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStore);
    }

    @PostMapping("/benefitInsert")
    public ResponseEntity<BenefitEntity> createBenefit(@RequestBody BenefitEntity benefit) {
        BenefitEntity savedBenefit = benefitRepository.save(benefit);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBenefit);
    }

    @PostMapping("/myBenefitInsert")
    public ResponseEntity<MyBenefitEntity> createMyBenefit(@RequestBody MyBenefitEntity myBenefit) {

        BenefitEntity benefit = benefitRepository.findById(myBenefit.getBenefit().getBenefitId())
                .orElseThrow(() -> new EntityNotFoundException("Benefit not found"));
        CardEntity card = cardRepository.findById(myBenefit.getCard().getCardId())
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));

        MyBenefitId myBenefitId = new MyBenefitId(benefit.getBenefitId(), card.getCardId());
        myBenefit.setMyBenefitId(myBenefitId);

        myBenefit.setBenefit(benefit);
        myBenefit.setCard(card);

        MyBenefitEntity savedMyBenefit = myBenefitRepository.save(myBenefit);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMyBenefit);
    }

//    @PostMapping("/paymentInsert")
//    public PaymentResponse createPayment(@RequestBody PosRequest posRequest) {
//        return PaymentResponse payment = paymentService.processPayment(posRequest);
//    }
}
