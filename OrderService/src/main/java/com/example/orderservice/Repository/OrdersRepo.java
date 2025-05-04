package com.example.orderservice.Repository;

import com.example.orderservice.Entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepo extends JpaRepository<OrdersEntity,Long> {
}
