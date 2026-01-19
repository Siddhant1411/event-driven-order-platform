package com.company.order.mapper;

import com.company.order.dto.OrderRequest;
import com.company.order.dto.OrderResponse;
import com.company.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public static Order toEntity(OrderRequest request){
        com.company.order.entity.Order order = new com.company.order.entity.Order();
        order.setProductName(request.getProductName());
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice());
        return order;
    }

    public OrderResponse toResponse(Order order){
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setProductName(order.getProductName());
        response.setQuantity(order.getQuantity());
        response.setPrice(order.getPrice());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }
}
