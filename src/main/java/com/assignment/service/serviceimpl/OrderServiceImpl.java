package com.assignment.service.serviceimpl;

import com.assignment.domain.Order;
import com.assignment.domain.Orderdetail;
import com.assignment.domain.User;
import com.assignment.model.OrderDetails;
import com.assignment.repository.OrderRepository;
import com.assignment.service.OrderDetailService;
import com.assignment.service.OrderService;
import com.assignment.service.ProductService;
import com.assignment.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository repository;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Override
    public Page<Order> findByUser_Username(Pageable pageable, String username) {
        return repository.findByUser_Username(pageable, username);
    }

    @Override
    public Order create(JsonNode orderData){

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<OrderDetails>> type = new TypeReference<List<OrderDetails>>() {
        };
        com.assignment.model.Order orderModel = mapper.convertValue(orderData, com.assignment.model.Order.class);
        Order orderEntity = new Order();
        BeanUtils.copyProperties(orderModel, orderEntity);
        Optional<User> user = userService.findById(orderModel.getUser());
        orderEntity.setUser(user.get());
        System.out.println(orderEntity);

        save(orderEntity);
        List<Orderdetail> details = mapper.convertValue(orderData.get("orderDetails"), type)
                .stream().map(item -> {
                    Orderdetail orderdetail = new Orderdetail();
                    orderdetail.setPrice(item.getPrice());
                    orderdetail.setQuantity(item.getQuantity());
                    orderdetail.setOrder(orderEntity);
                    orderdetail.setProduct(productService.findById(item.getProduct_id()).get());
                    return orderdetail;
                }).collect(Collectors.toList());
        orderDetailService.saveAll(details);

        return orderEntity;

    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends Order> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Order> findById(Integer integer) {
        return repository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return repository.existsById(integer);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void deleteById(Integer integer) {
        repository.deleteById(integer);
    }

    @Override
    public void delete(Order entity) {
        repository.delete(entity);
    }
}
