package com.assignment.service;

import com.assignment.domain.Commodity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CommodityService {
    List<Commodity> findAll();

    List<Commodity> findAll(Sort sort);

    List<Commodity> findAllById(Iterable<Integer> integers);

    Page<Commodity> findAll(Pageable pageable);

    <S extends Commodity> S save(S entity);

    Optional<Commodity> findById(Integer integer);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer integer);

    void delete(Commodity entity);

    List<Commodity> findByNameContaining(String name);

    Page<Commodity> findByNameContaining(String name, Pageable pageable);
}
