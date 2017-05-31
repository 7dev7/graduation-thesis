package com.dev.domain.repository;

import com.dev.domain.model.doctor.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {
    @Override
    List<Role> findAll();

    Role findByAuthority(String roleName);
}
