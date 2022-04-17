package com.assignment.repository;

import com.assignment.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findByUser_Username(Pageable pageable, String username);

}