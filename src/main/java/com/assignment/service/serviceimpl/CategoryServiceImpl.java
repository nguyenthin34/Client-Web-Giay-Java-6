package com.assignment.service.serviceimpl;

import com.assignment.domain.Category;
import com.assignment.repository.CategoryRepository;
import com.assignment.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository repository;

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Category> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public List<Category> findAllById(Iterable<Integer> integers) {
        return repository.findAllById(integers);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends Category> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Category> findById(Integer integer) {
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
    public void delete(Category entity) {
        repository.delete(entity);
    }
}
