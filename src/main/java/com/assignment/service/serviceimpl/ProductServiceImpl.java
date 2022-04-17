package com.assignment.service.serviceimpl;

import com.assignment.domain.Product;
import com.assignment.repository.ProductRepository;
import com.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;


//    @Override
//    public List<Product> findByColor_ColorIdAndSize_SizeIdAndCommodity_CommodityIdOrderByQuantity(Integer colorId, Integer sizeId, Integer commodityId) {
//        return repository.findByColor_ColorIdAndSize_SizeIdAndCommodity_CommodityIdOrderByQuantity(colorId, sizeId, commodityId);
//    }

    @Override
    public List<Product> findByProductByCommodityId(Integer commodityId) {
        return repository.findByProductByCommodityId(commodityId);
    }

    @Override
    public Product getById(Integer integer) {
        return repository.getById(integer);
    }

    @Override
    public List<Product> findByCommodity_Commodity_id(Integer commodityId) {
        return repository.findByCommodity_Commodity_id(commodityId);
    }
    @Override
    public Page<Product> findByCommodity_Commodity_id(Integer commodityId, Pageable pageable) {
        return repository.findByCommodity_Commodity_id(commodityId, pageable);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Product> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public List<Product> findAllById(Iterable<Integer> integers) {
        return repository.findAllById(integers);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends Product> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Product> findById(Integer integer) {
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
    public void delete(Product entity) {
        repository.delete(entity);
    }


}
