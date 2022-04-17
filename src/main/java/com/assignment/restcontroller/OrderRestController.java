package com.assignment.restcontroller;

import com.assignment.domain.Order;
import com.assignment.repository.OrderRepository;
import com.assignment.service.OrderDetailService;
import com.assignment.service.OrderService;
import com.assignment.service.ProductService;
import com.assignment.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/orders")
public class OrderRestController {
    @Autowired
    OrderRepository repository;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @PostMapping("")
    public Order create(@RequestBody JsonNode orderdata) {
        return orderService.create(orderdata);
    }

    @GetMapping("")
    public ResponseEntity<List<Order>> getAllOrder(){
        if(orderService.findAll().size() <= 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orderService.findAll());
    }
}
