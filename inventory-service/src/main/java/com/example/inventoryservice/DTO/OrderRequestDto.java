package com.example.inventoryservice.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private List<OrderRequestItemDto> items;
    private String status;
}
