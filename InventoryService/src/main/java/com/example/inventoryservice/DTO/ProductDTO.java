package com.example.inventoryservice.DTO;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String title;
    private Double price;
    private Integer stock;
}
