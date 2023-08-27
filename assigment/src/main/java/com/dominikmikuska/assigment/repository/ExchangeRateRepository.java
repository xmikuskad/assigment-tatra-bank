package com.dominikmikuska.assigment.repository;

import com.dominikmikuska.assigment.entity.CurrencyType;
import com.dominikmikuska.assigment.entity.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity,Long> {

    Optional<ExchangeRateEntity> findByFromCurrencyAndToCurrency(CurrencyType fromCurrency, CurrencyType toCurrency);
}
