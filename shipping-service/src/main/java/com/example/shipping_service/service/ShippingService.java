package com.example.shipping_service.service;

import com.example.shipping_service.model.Shipment;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ShippingService {

    private final ConcurrentHashMap<Long, Shipment>
            shipments = new ConcurrentHashMap<>();

    private final AtomicLong idGenerator =
            new AtomicLong(1);

    public Shipment createShipment(Long orderId) {

        Long shipmentId =
                idGenerator.getAndIncrement();

        Shipment shipment =
                new Shipment(
                        shipmentId,
                        orderId,
                        "CREATED"
                );

        shipments.put(
                shipmentId,
                shipment
        );

        return shipment;
    }

    public Shipment getShipment(Long id) {
        return shipments.get(id);
    }
}