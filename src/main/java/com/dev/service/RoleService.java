package com.dev.service;

import com.dev.domain.model.doctor.Role;

public interface RoleService {
    Role getRoleByRoleName(String roleName);
}
