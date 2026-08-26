package com.example.shipping_service.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    private Long id;
    private Long orderId;
    private String status;
}
