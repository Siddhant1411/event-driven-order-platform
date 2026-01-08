package com.company.order.service;

import com.company.order.dto.OrderRequest;
import com.company.order.dto.OrderResponse;
import com.company.order.entity.Order;
import com.company.order.mapper.OrderMapper;
import com.company.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponse createOrder(OrderRequest request){
        Order order = OrderMapper.toEntity(request);
        Order saveOrder = orderRepository.save(order);
        return OrderMapper.toResponse(saveOrder);
    }

    @Override
    public OrderResponse getOrderById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));
        return OrderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders(){
        return  orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }
}
