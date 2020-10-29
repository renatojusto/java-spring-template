package com.arch.stock.service.dto.request;

import com.arch.stock.config.MoneyDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDecreaseRequest {

    @NotNull
    @Digits(integer = 15, fraction = 8)
    @JsonProperty("quantity")
    @JsonDeserialize(using = MoneyDeserializer.class)
    private BigDecimal quantity;

    public ProductDecreaseRequest() {
    }

    public ProductDecreaseRequest(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        if (quantity == null) {
            quantity = BigDecimal.ZERO;
        }
        return quantity.setScale(8, RoundingMode.HALF_UP);
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
