package com.arch.stock.service.dto.response;

import com.arch.stock.config.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class ProductResponse extends RepresentationModel<ProductResponse> implements Serializable {

    private Long id;
    private String name;
    private String unitPrice;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal quantityStock;
    private Date dateCreation;
    private Date dateLastUpdate;
    private SupplierResponse supplier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(BigDecimal quantityStock) {
        this.quantityStock = quantityStock;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateLastUpdate() {
        return dateLastUpdate;
    }

    public void setDateLastUpdate(Date dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }

    public SupplierResponse getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierResponse supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductResponse that = (ProductResponse) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(quantityStock, that.quantityStock) &&
            Objects.equals(dateCreation, that.dateCreation) &&
            Objects.equals(dateLastUpdate, that.dateLastUpdate) &&
            Objects.equals(supplier, that.supplier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, unitPrice, quantityStock, dateCreation, dateLastUpdate, supplier);
    }
}
