package com.assignment.service;

import com.assignment.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductService {

//    List<Product> findByColor_ColorIdAndSize_SizeIdAndCommodity_CommodityIdOrderByQuantity(Integer colorId, Integer sizeId, Integer commodityId);

    List<Product> findByProductByCommodityId(Integer commodityId);

    Product getById(Integer integer);

    List<Product> findByCommodity_Commodity_id(Integer commodityId);

    Page<Product> findByCommodity_Commodity_id(Integer commodityId, Pageable pageable);

    List<Product> findAll();

    List<Product> findAll(Sort sort);

    List<Product> findAllById(Iterable<Integer> integers);

    Page<Product> findAll(Pageable pageable);

    <S extends Product> S save(S entity);

    Optional<Product> findById(Integer integer);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer integer);

    void delete(Product entity);
}
