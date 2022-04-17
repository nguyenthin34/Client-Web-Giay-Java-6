package com.assignment.service.serviceimpl;

import com.assignment.domain.Role;
import com.assignment.repository.RoleRepository;
import com.assignment.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository repository;

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Role> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Role getById(Integer integer) {
        return repository.getById(integer);
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends Role> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Role> findById(Integer integer) {
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
    public void delete(Role entity) {
        repository.delete(entity);
    }
}
