package com.arch.stock.domain;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.Objects;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Digits(integer = 15, fraction = 8)
    @Column(name = "quantity_stock", nullable = false, precision = 15, scale = 8)
    private BigDecimal quantityStock = BigDecimal.ZERO;

    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "unit_price", nullable = false)
    private String unitPrice;

    @NotNull
    @Column(name = "date_creation", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @Column(name = "date_last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastUpdate;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    @NotNull
    @Version
    @Column(name = "version", nullable = false)
    private Long version;

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

    public void increaseQuantityStock(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }
        this.quantityStock = quantityStock.add(value, MathContext.DECIMAL64);
    }

    public void decreaseQuantityStock(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }
        this.quantityStock = quantityStock.subtract(value, MathContext.DECIMAL64);
    }

    public BigDecimal getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(BigDecimal quantityStock) {
        this.quantityStock = quantityStock;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
