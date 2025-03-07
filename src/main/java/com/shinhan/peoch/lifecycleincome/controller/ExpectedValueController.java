package com.shinhan.peoch.lifecycleincome.controller;


import com.shinhan.peoch.lifecycleincome.service.ExpectedValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExpectedValueController {

    @Autowired
    private ExpectedValueService expectedValueService;

    @GetMapping("/expectedvalue/{userId}")
    public Double getExpectedValue(@PathVariable Integer userId) {
        return expectedValueService.calculatePresentValue(userId);
    }
}
