package com.shinhan.peoch.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDataResponseDTO {
    private Integer weekCurrent;
    private Integer weekPrevious;
    private Integer monthCurrent;
    private Integer monthPrevious;
    private Integer yearCurrent;
    private Integer yearPrevious;
    private Integer avgWeekCurrent;
    private Integer avgWeekPrevious;
    private Integer avgMonthCurrent;
    private Integer avgMonthPrevious;
    private Integer avgYearCurrent;
    private Integer avgYearPrevious;
}
