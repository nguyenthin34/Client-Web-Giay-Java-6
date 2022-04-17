package com.assignment.service;

import com.assignment.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();

    List<Role> findAll(Sort sort);

    Role getById(Integer integer);

    Page<Role> findAll(Pageable pageable);

    <S extends Role> S save(S entity);

    Optional<Role> findById(Integer integer);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer integer);

    void delete(Role entity);
}
