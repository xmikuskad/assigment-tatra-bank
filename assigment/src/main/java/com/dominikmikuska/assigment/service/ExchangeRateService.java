package com.dominikmikuska.assigment.service;

import com.dominikmikuska.assigment.entity.CurrencyType;
import com.dominikmikuska.assigment.entity.ExchangeRateEntity;
import com.dominikmikuska.assigment.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;


    public List<ExchangeRateEntity> findAll() {
        return this.exchangeRateRepository.findAll();
    }

    public Optional<ExchangeRateEntity> findByFromCurrencyAndToCurrency(CurrencyType fromCurrency, CurrencyType toCurrency) {
        return this.exchangeRateRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
    }
}
