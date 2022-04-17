package com.assignment.service;

import com.assignment.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findByEmailContaining(String email);

    List<User> findAll();

    List<User> findAll(Sort sort);

    User getById(String s);

    Page<User> findAll(Pageable pageable);

    <S extends User> S save(S entity);

    <S extends User> S update(S entity);

    Optional<User> findById(String s);

    boolean existsById(String s);

    long count();

    void deleteById(String s);

    void delete(User entity);
}
