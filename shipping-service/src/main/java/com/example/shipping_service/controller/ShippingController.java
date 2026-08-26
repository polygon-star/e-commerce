package com.example.shipping_service.controller;

import com.example.shipping_service.dto.CreateShipmentRequest;
import com.example.shipping_service.model.Shipment;
import com.example.shipping_service.service.ShippingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipments")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(
            ShippingService shippingService
    ) {
        this.shippingService =
                shippingService;
    }

    @PostMapping
    public Shipment createShipment(
            @RequestBody CreateShipmentRequest request
    ) {

        return shippingService.createShipment(
                request.orderId()
        );
    }

    @GetMapping("/{id}")
    public Shipment getShipment(
            @PathVariable Long id
    ) {

        return shippingService.getShipment(id);
    }
}
