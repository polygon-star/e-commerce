package com.example.order_service.client;

import com.example.order_service.dto.CreateShipmentRequest;
import com.example.order_service.dto.ShipmentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ShippingClient {

    private final RestTemplate restTemplate;

    @Value("${shipping.service.url}")
    private String shippingServiceUrl;

    public ShippingClient(
            RestTemplate restTemplate
    ) {
        this.restTemplate = restTemplate;
    }

    public ShipmentResponse createShipment(Long orderId) {

        CreateShipmentRequest request =
                new CreateShipmentRequest(orderId);

        return restTemplate.postForObject(
                shippingServiceUrl + "/shipments",
                request,
                ShipmentResponse.class
        );
    }
}
