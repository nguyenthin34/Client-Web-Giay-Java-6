package com.assignment.repository;

import com.assignment.domain.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Integer> {
}