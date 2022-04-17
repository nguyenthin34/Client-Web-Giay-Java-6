package com.assignment.service.serviceimpl;

import com.assignment.domain.User;
import com.assignment.repository.UserRepository;
import com.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public User findByEmailContaining(String email) {
        return repository.findByEmailContaining(email);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<User> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public User getById(String s) {
        return repository.getById(s);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends User> S save(S entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return repository.save(entity);
    }

    @Override
    public <S extends User> S update(S entity) {

        return repository.save(entity);
    }

    @Override
    public Optional<User> findById(String s) {
        return repository.findById(s);
    }

    @Override
    public boolean existsById(String s) {
        return repository.existsById(s);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void deleteById(String s) {
        repository.deleteById(s);
    }

    @Override
    public void delete(User entity) {
        repository.delete(entity);
    }
}
