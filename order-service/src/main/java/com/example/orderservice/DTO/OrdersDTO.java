package com.example.orderservice.DTO;

import com.example.orderservice.Entity.OrderItemsEntity;
import com.example.orderservice.Entity.OrdersStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

import java.util.List;

public class OrdersDTO {
    private Long id;

    private OrdersStatus ordersStatus;

    private Double price;

    private List<OrderItemsEntity> items;
}
