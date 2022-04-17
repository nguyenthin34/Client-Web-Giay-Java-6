package com.assignment.service.serviceimpl;

import com.assignment.domain.Orderdetail;
import com.assignment.repository.OrderdetailRepository;
import com.assignment.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    OrderdetailRepository repository;

//    @Override
//    public Page<Orderdetail> findByOrder_Order_id(Pageable pageable, Integer orderId) {
//        return repository.findByOrder_Order_id(pageable, orderId);
//    }

    @Override
    public List<Orderdetail> findAll() {
        return repository.findAll();
    }

    @Override
    public <S extends Orderdetail> List<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public List<Orderdetail> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<Orderdetail> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends Orderdetail> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Orderdetail> findById(Integer integer) {
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
    public void delete(Orderdetail entity) {
        repository.delete(entity);
    }
}
