package com.example.order_service.kafka;

import com.example.order_service.client.ShippingClient;
import com.example.order_service.event.InventoryEvent;
import com.example.order_service.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventConsumer {

    private final OrderService orderService;
    private final ShippingClient shippingClient;

    public InventoryEventConsumer(
            OrderService orderService,
            ShippingClient shippingClient
    ) {
        this.orderService = orderService;
        this.shippingClient = shippingClient;
    }

    @KafkaListener(
            topics = "inventory-events",
            groupId = "order-service"
    )
    public void handleInventoryEvent(
            InventoryEvent event
    ) {

        if ("RESERVED".equals(event.status())) {

            orderService.confirmOrder(
                    event.orderId()
            );

            shippingClient.createShipment(
                    event.orderId()
            );

        } else {

            orderService.rejectOrder(
                    event.orderId()
            );
        }
    }
}
