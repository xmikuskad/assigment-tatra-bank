package com.dominikmikuska.assigment.controller;

import com.dominikmikuska.assigment.dto.ExchangeRateAmountDto;
import com.dominikmikuska.assigment.dto.ExchangeRateDto;
import com.dominikmikuska.assigment.entity.ExchangeRateEntity;
import com.dominikmikuska.assigment.service.ExchangeRateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping(path="/exchange-rates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExchangeRateDto>> getExchangeRates() {
        List<ExchangeRateDto> dtos = this.exchangeRateService.findAll().stream().map(i->
                ExchangeRateDto.builder().fromCurrency(i.getFromCurrency()).toCurrency(i.getToCurrency()).value(i.getValue()).build()
        ).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(path="/exchange", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExchangeRateAmountDto> getExchangeRates(@RequestBody ExchangeRateDto exchangeRateDto) {
        if(exchangeRateDto == null || exchangeRateDto.hasEmptyField() || exchangeRateDto.getValue().compareTo(BigDecimal.ZERO) <= 0)
            return ResponseEntity.badRequest().build();

        ExchangeRateEntity entity = this.exchangeRateService.findByFromCurrencyAndToCurrency(exchangeRateDto.getFromCurrency(),exchangeRateDto.getToCurrency()).orElse(null);
        if(entity == null)
            return ResponseEntity.badRequest().build();

        // Not sure how the rounding works in real life applications, so I used classic round half up
        ExchangeRateAmountDto dto = ExchangeRateAmountDto.builder().amount(entity.getValue().multiply(exchangeRateDto.getValue()).setScale(2, RoundingMode.HALF_UP)).build();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
