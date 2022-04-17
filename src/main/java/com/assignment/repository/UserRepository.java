package com.assignment.repository;

import com.assignment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmailContaining(String email);

    /**
     * Check exists an user email
     *
     * @param email
     * @return Boolean
     */
    Boolean existsByEmail(String email);
}