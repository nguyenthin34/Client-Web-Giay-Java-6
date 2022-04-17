package com.assignment.repository;

import com.assignment.domain.Orderdetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderdetailRepository extends JpaRepository<Orderdetail, Integer> {
//     Page<Orderdetail> findByOrder_Order_id(Pageable pageable, Integer orderId);
}