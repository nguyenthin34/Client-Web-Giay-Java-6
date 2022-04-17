package com.assignment.service;

import com.assignment.domain.Order;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Page<Order> findByUser_Username(Pageable pageable, String username);

    Order create(JsonNode orderDate);

    List<Order> findAll();

    Page<Order> findAll(Pageable pageable);

    <S extends Order> S save(S entity);

    Optional<Order> findById(Integer integer);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer integer);

    void delete(Order entity);
}
