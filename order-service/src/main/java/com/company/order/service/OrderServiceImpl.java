package com.company.order.service;

import com.company.order.dto.OrderRequest;
import com.company.order.dto.OrderResponse;
import com.company.order.entity.Order;
import com.company.order.event.OrderCreatedEvent;
import com.company.order.kafka.OrderEventProducer;
import com.company.order.mapper.OrderMapper;
import com.company.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderEventProducer orderEventProducer,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderEventProducer = orderEventProducer;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponse createOrder(OrderRequest request) {

        // 1️⃣ Map request → entity
        Order order = OrderMapper.toEntity(request);

        // 2️⃣ Save to DB
        Order savedOrder = orderRepository.save(order);

        // 3️⃣ Create Kafka event
        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                String.valueOf(savedOrder.getUserId()),
                savedOrder.getAmount(),
                savedOrder.getStatus()
        );

        // 4️⃣ Publish to Kafka
        orderEventProducer.publishOrderCreatedEvent(event);

        // 5️⃣ Return response
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }
}
