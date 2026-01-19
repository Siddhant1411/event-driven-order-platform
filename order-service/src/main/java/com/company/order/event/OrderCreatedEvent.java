package com.company.order.event;

import java.math.BigDecimal;

public class OrderCreatedEvent {

    private String orderId;
    private String userId;
    private BigDecimal amount;
    private String status;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(String orderId, String userId,
                             BigDecimal amount, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
    }

    public OrderCreatedEvent(Long id, String userId, BigDecimal amount, String status) {
    }

    public String getOrderId() {
        return orderId;
    }
    // getters & setters
}
