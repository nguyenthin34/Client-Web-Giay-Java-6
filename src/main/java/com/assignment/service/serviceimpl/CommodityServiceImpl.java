package com.assignment.service.serviceimpl;

import com.assignment.domain.Commodity;
import com.assignment.repository.CommodityRepository;
import com.assignment.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    CommodityRepository repository;

    @Override
    public List<Commodity> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Commodity> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public List<Commodity> findAllById(Iterable<Integer> integers) {
        return repository.findAllById(integers);
    }

    @Override
    public Page<Commodity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends Commodity> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Commodity> findById(Integer integer) {
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
    public void delete(Commodity entity) {
        repository.delete(entity);
    }

    @Override
    public List<Commodity> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

    @Override
    public Page<Commodity> findByNameContaining(String name, Pageable pageable) {
        return repository.findByNameContaining(name, pageable);
    }
}
