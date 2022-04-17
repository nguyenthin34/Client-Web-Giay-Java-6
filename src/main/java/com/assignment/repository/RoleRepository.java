package com.assignment.repository;

import com.assignment.common.ERole;
import com.assignment.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Find role by name
     @param name
     @return Role
     */
    Optional<Role> findByName (ERole name);
}