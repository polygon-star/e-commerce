package com.example.order_service.service;

import com.example.order_service.event.OrderCreatedEvent;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final ConcurrentHashMap<Long, Order> orders =
            new ConcurrentHashMap<>();

    private final AtomicLong idGenerator =
            new AtomicLong(1);

    private final KafkaTemplate<String, OrderCreatedEvent>
            kafkaTemplate;

    public OrderService(
            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Order createOrder(
            Long productId,
            int quantity
    ) {

        Long orderId =
                idGenerator.getAndIncrement();

        Order order =
                new Order(
                        orderId,
                        productId,
                        quantity,
                        OrderStatus.PENDING
                );

        orders.put(orderId, order);

        OrderCreatedEvent event =
                new OrderCreatedEvent(
                        orderId,
                        productId,
                        quantity
                );

        kafkaTemplate.send(
                "order-events",
                orderId.toString(),
                event
        );

        return order;
    }

    public Order getOrder(Long id) {
        return orders.get(id);
    }

    public void confirmOrder(Long orderId) {

        Order order = orders.get(orderId);

        if (order != null) {
            order.setStatus(
                    OrderStatus.CONFIRMED
            );
        }
    }

    public void rejectOrder(Long orderId) {

        Order order = orders.get(orderId);

        if (order != null) {
            order.setStatus(
                    OrderStatus.REJECTED
            );
        }
    }
}
