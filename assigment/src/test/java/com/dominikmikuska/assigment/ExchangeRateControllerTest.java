package com.dominikmikuska.assigment;


import com.dominikmikuska.assigment.dto.ExchangeRateDto;
import com.dominikmikuska.assigment.entity.CurrencyType;
import com.dominikmikuska.assigment.entity.ExchangeRateEntity;
import com.dominikmikuska.assigment.repository.ExchangeRateRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeRateControllerTest {
    private final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    // <editor-fold desc="Region positive tests">
    @DisplayName("Test get all rates")
    @Test
    public void testGetAllRates() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/exchange-rates"))
                .andExpect(status().isOk()).andReturn();

        // Get rates from request and from db and then compare them. We sort them by the same properties to get same results.
        List<ExchangeRateDto> ratesFromRequest = MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
        ratesFromRequest.sort(Comparator.comparing(ExchangeRateDto::getFromCurrency).thenComparing(ExchangeRateDto::getToCurrency));
        List<ExchangeRateEntity> ratesFromDb = exchangeRateRepository.findAll().stream().sorted(
                Comparator.comparing(ExchangeRateEntity::getFromCurrency).thenComparing(ExchangeRateEntity::getToCurrency)
        ).toList();

        assertEquals(ratesFromDb.size(), ratesFromRequest.size());
        for (int i = 0; i < ratesFromDb.size(); i++) {
            ExchangeRateDto dto = ratesFromRequest.get(i);
            ExchangeRateEntity entity = ratesFromDb.get(i);
            assertEquals(dto.getFromCurrency(), entity.getFromCurrency());
            assertEquals(dto.getToCurrency(), entity.getToCurrency());
            assertEquals(dto.getValue(), entity.getValue());
        }
    }

    @DisplayName("Test post exchange calculations")
    @Test
    public void testPostExchangeCalculations() throws Exception {
        // Test variables
        BigDecimal testAmount = new BigDecimal("1.25");
        CurrencyType fromCurrency = CurrencyType.EUR;
        CurrencyType toCurrency = CurrencyType.USD;

        ExchangeRateEntity entity = exchangeRateRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency).orElse(null);
        ExchangeRateDto requestDto = ExchangeRateDto.builder().fromCurrency(fromCurrency).toCurrency(toCurrency).value(testAmount).build();
        mockMvc.perform(post("/api/exchange").contentType(MediaType.APPLICATION_JSON_VALUE).content(MAPPER.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(entity.getValue().multiply(testAmount).setScale(2, RoundingMode.HALF_UP).toString()));

    }

    // </editor-fold>

    // <editor-fold desc="Region negative tests">
    @DisplayName("Test post exchange field validation")
    @Test
    public void testPostExchangeFieldValidation() throws Exception {
        makePostExchangeWithValues(null, CurrencyType.USD, new BigDecimal("1.75"));
    }

    @DisplayName("Test post exchange negative number")
    @Test
    public void testPostExchangeNegativeNumber() throws Exception {
        makePostExchangeWithValues(CurrencyType.USD, CurrencyType.EUR, new BigDecimal("-3.66"));
    }

    @DisplayName("Test post exchange same currencies")
    @Test
    public void testPostExchangeSameCurrencies() throws Exception {
        makePostExchangeWithValues(CurrencyType.USD, CurrencyType.USD, new BigDecimal("2.5"));
    }
    // </editor-fold>

    private void makePostExchangeWithValues(CurrencyType fromCurrency, CurrencyType toCurrency, BigDecimal amount) throws Exception {
        ExchangeRateDto requestDto = ExchangeRateDto.builder().fromCurrency(fromCurrency).toCurrency(toCurrency).value(amount).build();
        mockMvc.perform(post("/api/exchange").contentType(MediaType.APPLICATION_JSON_VALUE).content(MAPPER.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }
}