package com.example.order_service.event;

public record OrderCreatedEvent(
        Long orderId,
        Long productId,
        int quantity
) {}
