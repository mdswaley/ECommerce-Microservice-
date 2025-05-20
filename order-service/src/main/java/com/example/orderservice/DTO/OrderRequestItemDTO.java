package com.example.orderservice.DTO;

import lombok.Data;

@Data
public class OrderRequestItemDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
}
