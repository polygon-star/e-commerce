package com.example.product_service.event;

public record InventoryEvent(
        Long orderId,
        Long productId,
        int quantity,
        String status
) {}
