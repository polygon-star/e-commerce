package com.example.order_service.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;

    private Long productId;

    private int quantity;

    private OrderStatus status;

}
