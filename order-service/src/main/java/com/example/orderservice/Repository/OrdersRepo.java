package com.example.orderservice.Repository;

import com.example.orderservice.Entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepo extends JpaRepository<OrdersEntity,Long> {
}
