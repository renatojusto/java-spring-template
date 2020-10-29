package com.arch.stock.service.dto.request;

import com.arch.stock.config.MoneyDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCreateRequest {

    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    @JsonProperty("name")
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 60)
    @JsonProperty("unitPrice")
    private String unitPrice;

    @NotNull
    @Digits(integer = 15, fraction = 8)
    @JsonProperty("initialQuantityStock")
    @JsonDeserialize(using = MoneyDeserializer.class)
    private BigDecimal initialQuantityStock;

    @NotNull
    @JsonProperty("supplierId")
    private Long supplierId;

    public ProductCreateRequest() {
    }

    public ProductCreateRequest(String name, String unitPrice, BigDecimal initialQuantityStock) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.initialQuantityStock = initialQuantityStock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getInitialQuantityStock() {
        if (initialQuantityStock == null) {
            initialQuantityStock = BigDecimal.ZERO;
        }
        return initialQuantityStock.setScale(8, RoundingMode.HALF_UP);
    }

    public void setInitialQuantityStock(BigDecimal initialQuantityStock) {
        this.initialQuantityStock = initialQuantityStock;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "ProductCreateRequest{" +
            "name='" + name + '\'' +
            ", unitPrice='" + unitPrice + '\'' +
            ", initialQuantityStock=" + initialQuantityStock +
            ", supplierId=" + supplierId +
            '}';
    }
}
