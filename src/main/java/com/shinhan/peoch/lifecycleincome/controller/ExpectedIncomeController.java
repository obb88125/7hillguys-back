package com.shinhan.peoch.lifecycleincome.controller;


import com.shinhan.entity.ExpectedIncomeEntity;
import com.shinhan.peoch.lifecycleincome.service.ExpectedIncomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/expectedincome")
public class ExpectedIncomeController {



    private final ExpectedIncomeService expectedIncomeService;

    public ExpectedIncomeController(ExpectedIncomeService expectedIncomeService) {
        this.expectedIncomeService = expectedIncomeService;
    }

    @GetMapping("/{userProfileId}")
    public ResponseEntity<List<ExpectedIncomeEntity>> getExpectedIncomes(

            @PathVariable Integer userProfileId) {
        System.out.println("접근은 했어!");
        List<ExpectedIncomeEntity> incomes = expectedIncomeService.getExpectedIncomesByUserProfileId(userProfileId);
        return ResponseEntity.ok(incomes);
    }
}
