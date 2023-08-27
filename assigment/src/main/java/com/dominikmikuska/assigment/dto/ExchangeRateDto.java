package com.dominikmikuska.assigment.dto;

import com.dominikmikuska.assigment.entity.CurrencyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExchangeRateDto {

    private CurrencyType fromCurrency;
    private CurrencyType toCurrency;
    private BigDecimal value;

    @JsonIgnore
    public boolean hasEmptyField() {
        return this.fromCurrency == null || this.toCurrency == null || this.value == null;
    }

}
