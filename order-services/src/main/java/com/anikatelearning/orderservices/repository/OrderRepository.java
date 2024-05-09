package com.anikatelearning.orderservices.repository;

import com.anikatelearning.orderservices.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
