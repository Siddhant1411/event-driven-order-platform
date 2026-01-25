package com.company.order.mapper;

import com.company.order.dto.OrderRequest;
import com.company.order.dto.OrderResponse;
import com.company.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public static Order toEntity(OrderRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setAmount(request.getAmount());
        order.setProductName(request.getProductName());
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice());
        // status & createdAt set by @PrePersist
        return order;
    }

    public OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId() != null ? Long.valueOf(order.getId().toString()) : null);  // ← FIXED: Long → String
        response.setProductName(order.getProductName());
        response.setQuantity(order.getQuantity());
        response.setPrice(order.getPrice());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }
}