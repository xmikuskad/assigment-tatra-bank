package com.dominikmikuska.assigment.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExchangeRateAmountDto {

    private BigDecimal amount;
}
