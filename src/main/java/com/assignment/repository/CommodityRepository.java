package com.assignment.repository;

import com.assignment.domain.Commodity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommodityRepository extends JpaRepository<Commodity, Integer> {

    List<Commodity> findByNameContaining(String name);

    Page<Commodity> findByNameContaining(String name, Pageable pageable);

}