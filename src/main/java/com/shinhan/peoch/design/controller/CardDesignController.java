package com.shinhan.peoch.design.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.shinhan.peoch.design.dto.CardDesignDTO;
import com.shinhan.entity.CardDesignEntity;
import com.shinhan.peoch.design.service.CardDesignService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/card/design")
@RequiredArgsConstructor
public class CardDesignController {

    private final CardDesignService cardDesignService;

    @PostMapping("/insert")
    public ResponseEntity<String> createCardDesign(@RequestBody CardDesignDTO cardDesignDTO) {
        CardDesignEntity entity;
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22" + cardDesignDTO.toString());
        try {
            entity = CardDesignEntity.builder()
                    .username(cardDesignDTO.getUsername())
                    .layout_id(cardDesignDTO.getLayout_id())
                    .letterColor(cardDesignDTO.getLetterColor())
                    .image(cardDesignDTO.getImage().getBytes())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("카드 생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        cardDesignService.registerCardDesign(entity);
        return new ResponseEntity<>("카드 생성 완료", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<CardDesignEntity>> getAllCardDesigns() {
        List<CardDesignEntity> list = cardDesignService.getAllCards();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDesignEntity> getCardDesignById(@PathVariable("id") int id) {
        Optional<CardDesignEntity> entity = cardDesignService.getDesignById(id);
        return entity.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardDesign(@PathVariable("id") int id) {
        boolean deleted = cardDesignService.deleteCardById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
