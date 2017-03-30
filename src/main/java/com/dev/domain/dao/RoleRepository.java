package com.dev.domain.dao;

import com.dev.domain.model.users.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {
    @Override
    List<Role> findAll();

    Role findByAuthority(String roleName);
}
