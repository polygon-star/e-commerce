package com.example.order_service.dto;

public record ShipmentResponse(
        Long id,
        Long orderId,
        String status
) {}
