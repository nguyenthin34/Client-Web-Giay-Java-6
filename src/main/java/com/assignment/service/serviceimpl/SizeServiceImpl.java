package com.assignment.service.serviceimpl;

import com.assignment.domain.Size;
import com.assignment.repository.SizeRepository;
import com.assignment.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    SizeRepository repository;

    @Override
    public List<Size> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Size> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Size getById(Integer integer) {
        return repository.getById(integer);
    }

    @Override
    public Page<Size> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends Size> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Size> findById(Integer integer) {
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
    public void delete(Size entity) {
        repository.delete(entity);
    }
}
