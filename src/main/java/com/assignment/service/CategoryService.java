package com.assignment.service;

import com.assignment.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    List<Category> findAll(Sort sort);

    List<Category> findAllById(Iterable<Integer> integers);

    Page<Category> findAll(Pageable pageable);

    <S extends Category> S save(S entity);

    Optional<Category> findById(Integer integer);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer integer);

    void delete(Category entity);
}
