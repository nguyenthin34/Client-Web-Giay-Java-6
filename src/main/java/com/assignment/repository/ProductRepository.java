package com.assignment.repository;

import com.assignment.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p " +
            " where p.quantity > 0 " +
            " group by p.commodity")
    Page<Product> getAllByCommodity(Pageable pageable);

    @Query("SELECT p FROM Product p where p.commodity.commodity_id = ?1")
    List<Product> findByCommodity_Commodity_id(Integer commodityId);
    @Query("SELECT p FROM Product p where p.commodity.commodity_id = ?1")
    Page<Product> findByCommodity_Commodity_id(Integer commodityId, Pageable pageable);

    @Query("SELECT p FROM Product p where p.commodity.commodity_id = ?1" +
            " ORDER BY p.price desc ")
    List<Product> findByProductByCommodityId(Integer commodityId);
//    List<Product> findByColor_ColorIdAndSize_SizeIdAndCommodity_CommodityIdOrderByQuantity(Long colorId, Long sizeId, Long commodityId);
}