package com.example.orderservice.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "order_items")
public class OrderItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrdersEntity orders;
}
