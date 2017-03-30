package com.dev.service;

import com.dev.domain.dao.RoleRepository;
import com.dev.domain.model.users.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class RoleManager {
    public static final Map<String, Role> ROLES = new HashMap<>();
    private final RoleRepository roleRepository;

    @Autowired
    public RoleManager(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        ROLES.put("DOCTOR", roleRepository.findByAuthority("ROLE_CONTACT"));
        ROLES.put("PATIENT", roleRepository.findByAuthority("ROLE_TEACHER"));
    }

    public Role getRoleByRoleName(String roleName) {
        return ROLES.get(roleName);
    }
}
