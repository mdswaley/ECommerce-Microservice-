package com.example.orderservice.DTO;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
public class OrderRequestItemDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
}
