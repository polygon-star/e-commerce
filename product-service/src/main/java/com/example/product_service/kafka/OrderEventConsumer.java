package com.example.product_service.kafka;

import com.example.product_service.event.InventoryEvent;
import com.example.product_service.event.OrderCreatedEvent;
import com.example.product_service.service.ProductService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    private final ProductService productService;
    private final KafkaTemplate<String, InventoryEvent> kafkaTemplate;

    public OrderEventConsumer(
            ProductService productService,
            KafkaTemplate<String, InventoryEvent> kafkaTemplate
    ) {
        this.productService = productService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(
            topics = "order-events",
            groupId = "product-service"
    )
    public void handleOrderCreated(OrderCreatedEvent event) {

        // 1. ProductService checks and subtracts inventory
        boolean reserved = productService.reserveInventory(
                event.productId(),
                event.quantity()
        );

        // 2. create InventoryEvent according to result
        InventoryEvent inventoryEvent = new InventoryEvent(
                event.orderId(),
                event.productId(),
                event.quantity(),
                reserved ? "RESERVED" : "REJECTED"
        );

        // 3. send results to Kafka
        kafkaTemplate.send(
                "inventory-events",
                event.orderId().toString(),
                inventoryEvent
        );
    }
}
