package com.shinhan.peoch.UserProfileNormalization.service;

import com.shinhan.peoch.UserProfileNormalization.perplexity.UserProfileNormalizationPerplexityService;
import com.shinhan.peoch.lifecycleincome.service.InvestmentService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncProcessingService {

    private UserProfileNormalizationPerplexityService userProfileNormalizationPerplexityService;
    private InvestmentService investmentService;
    //비동기로 처리하는 함수 모음

    //스레드 관련 설정은 asyncConfig에서 함
    //profile이 설정된 순간 부터 해당 프로필로 쭉 처리
    @Async("asyncExecutor")
    public CompletableFuture<Void> profileToExpectedIncome(int userProfileId,int userId) {
        userProfileNormalizationPerplexityService.normalizeAndSaveUserProfile(userProfileId);
        investmentService.createOrUpdateInvestment(userId);
        return CompletableFuture.completedFuture(null);
    }
}
