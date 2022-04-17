package com.assignment.service;

import com.assignment.domain.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface SizeService {
    List<Size> findAll();

    List<Size> findAll(Sort sort);

    Size getById(Integer integer);

    Page<Size> findAll(Pageable pageable);

    <S extends Size> S save(S entity);

    Optional<Size> findById(Integer integer);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer integer);

    void delete(Size entity);
}
