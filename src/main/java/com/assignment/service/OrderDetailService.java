package com.assignment.service;

import com.assignment.domain.Orderdetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderDetailService {
//    Page<Orderdetail> findByOrder_Order_id(Pageable pageable, Integer orderId);

    List<Orderdetail> findAll();

    <S extends Orderdetail> List<S> saveAll(Iterable<S> entities);

    List<Orderdetail> findAll(Sort sort);

    Page<Orderdetail> findAll(Pageable pageable);

    <S extends Orderdetail> S save(S entity);

    Optional<Orderdetail> findById(Integer integer);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer integer);

    void delete(Orderdetail entity);
}
