package com.assignment.service.serviceimpl;

import com.assignment.domain.Color;
import com.assignment.repository.ColorRepository;
import com.assignment.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    ColorRepository repository;

    @Override
    public List<Color> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Color> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Color getById(Integer integer) {
        return repository.getById(integer);
    }

    @Override
    public Page<Color> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends Color> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Color> findById(Integer integer) {
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
    public void delete(Color entity) {
        repository.delete(entity);
    }
}
