package com.example.inventoryservice.DTO;

import lombok.Data;

@Data
public class OrderRequestItemDto {
    private Long productId;
    private Integer quantity;
}
