package com.shinhan.peoch.lifecycleincome.service;

import com.shinhan.entity.ExpectedIncomeEntity;
import com.shinhan.repository.ExpectedIncomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpectedIncomeService {

    private final ExpectedIncomeRepository expectedIncomeRepository;

    public ExpectedIncomeService(ExpectedIncomeRepository expectedIncomeRepository) {
        this.expectedIncomeRepository = expectedIncomeRepository;
    }

    public List<ExpectedIncomeEntity> getExpectedIncomesByUserProfileId(Integer userProfileId) {
        return expectedIncomeRepository.findByUserProfileId(userProfileId);
    }
}
