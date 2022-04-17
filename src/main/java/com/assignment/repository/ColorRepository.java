package com.assignment.repository;

import com.assignment.domain.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Integer> {
}