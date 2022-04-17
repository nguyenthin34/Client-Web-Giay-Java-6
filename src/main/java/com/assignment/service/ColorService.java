package com.assignment.service;

import com.assignment.domain.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ColorService {
    List<Color> findAll();

    List<Color> findAll(Sort sort);

    Color getById(Integer integer);

    Page<Color> findAll(Pageable pageable);

    <S extends Color> S save(S entity);

    Optional<Color> findById(Integer integer);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer integer);

    void delete(Color entity);
}
