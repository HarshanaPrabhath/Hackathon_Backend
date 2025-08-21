package com.solution.hacktrail.repositories;


import com.solution.hacktrail.model.AppRole;
import com.solution.hacktrail.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(AppRole appRole);
}
