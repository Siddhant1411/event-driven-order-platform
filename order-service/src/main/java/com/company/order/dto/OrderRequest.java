package com.company.order.dto;

import java.math.BigDecimal;

public class OrderRequest {

    private Long userId;          // ← added (required for most order systems)
    private String productName;
    private Integer quantity;
    private BigDecimal price;     // ← changed to BigDecimal (better for money)
    private BigDecimal amount;    // ← optional: can be sent or computed

    // Getters & Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}