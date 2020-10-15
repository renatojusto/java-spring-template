package com.arch.stock.service.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductUpdateRequest {

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
    @JsonProperty("supplierId")
    private Long supplierId;

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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "ProductUpdateRequest{" +
            "name='" + name + '\'' +
            ", unitPrice='" + unitPrice + '\'' +
            ", supplierId=" + supplierId +
            '}';
    }
}
