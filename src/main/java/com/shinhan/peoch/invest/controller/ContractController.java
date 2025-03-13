package com.shinhan.peoch.invest.controller;

import com.shinhan.entity.InvestmentEntity;
import com.shinhan.peoch.invest.dto.SignDTO;
import com.shinhan.repository.InvestmentRepository;
import com.shinhan.peoch.invest.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/contract")
@RequiredArgsConstructor
public class ContractController {
    private final InvestmentRepository investmentRepository;
    private final ContractService contractService;

    // 1.계약서 기본 형식 반환 (프론트엔드에서 계약서 미리보기)
    @GetMapping("/template/{userId}")
    public ResponseEntity<Map<String, Object>> getContractTemplate(@PathVariable Integer userId) {
        Optional<InvestmentEntity> investmentOpt = investmentRepository.findById(userId);

        if (investmentOpt.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "해당 사용자의 계약서 데이터를 찾을 수 없습니다.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        InvestmentEntity investment = investmentOpt.get();

        // 계약서 기본 내용 (사용자 데이터 포함)
        Map<String, Object> contractData = new HashMap<>();
        contractData.put("title", "대출 계약서");
        contractData.put("investmentDetails", String.format(
                "투자자는 %s부터 %s까지 매월 %d 원을 지급받으며, 총 %d 원을 지원받습니다. " +
                        "최대 투자 금액은 %d 원이며, 투자금액 변동에 따라 상환 비율이 조정됩니다.",
                investment.getStartDate(), investment.getEndDate(),
                investment.getMonthlyAllowance(), investment.getOriginalInvestValue(),
                investment.getMaxInvestment()));

        contractData.put("repaymentTerms", String.format(
                "돈을 갚는 날은 %s부터 시작되며, 55세까지 입니다. 최소 상환 금액은 0 원이고, 최대 상환 금액은 %d 원입니다.",
                investment.getEndDate()));

        contractData.put("agreements", new String[]{
                "본 계약서는 상호 동의 하에 체결됩니다.",
                "상환 일정은 변동될 수 있으며, 연체 시 이자가 부과될 수 있습니다."
        });

        return ResponseEntity.ok(contractData);
    }

    // 2.계약서 서명 후 PDF 생성 및 저장
    @PostMapping("/sign")
    public ResponseEntity<byte[]> signContract(@RequestBody SignDTO request) {
        try {
            byte[] pdf = contractService.generateAndSaveContractPdf(request.getUserId(), request.getBase64Signature());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "signed_contract.pdf");

            log.info("✅ 계약서 PDF 생성 성공! 크기: {} bytes", pdf.length);

            return ResponseEntity.ok().headers(headers).body(pdf);
        } catch (Exception e) {
            log.error("🚨 계약서 서명 중 오류 발생! userId: {}", request.getUserId(), e);
            return ResponseEntity.internalServerError().body(null);
        }
    }
}