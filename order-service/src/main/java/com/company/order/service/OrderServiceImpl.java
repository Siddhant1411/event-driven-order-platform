package com.company.order.service;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log =
            LoggerFactory.getLogger(OrderServiceImpl.class);
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
        Order order = OrderMapper.toEntity(request);

        // Debug: log the entity before save
        log.info("Order before save - userId={}, amount={}, status={}",
                order.getUserId(), order.getAmount(), order.getStatus());

        Order savedOrder = orderRepository.saveAndFlush(order);

        // Debug: log after save (should be same except id generated)
        log.info("Saved order - id={}, userId={}, amount={}, status={}",
                savedOrder.getId(), savedOrder.getUserId(), savedOrder.getAmount(), savedOrder.getStatus());

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId().toString(),   // explicit toString() if not already done
                savedOrder.getUserId(),
                savedOrder.getAmount(),
                savedOrder.getStatus()
        );

        // Debug: log event fields
        log.info("Publishing event - orderId={}, userId={}, amount={}, status={}",
                event.getOrderId(), event.getUserId(), event.getAmount(), event.getStatus());

        orderEventProducer.publishOrderCreatedEvent(event);

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
