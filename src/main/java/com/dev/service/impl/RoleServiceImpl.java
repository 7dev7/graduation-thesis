package com.dev.service.impl;

import com.dev.domain.dao.RoleRepository;
import com.dev.domain.model.doctor.Role;
import com.dev.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    public static final Map<String, Role> ROLES = new HashMap<>();
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        ROLES.put("DOCTOR", roleRepository.findByAuthority("ROLE_CONTACT"));
        ROLES.put("PATIENT", roleRepository.findByAuthority("ROLE_TEACHER"));
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        return ROLES.get(roleName);
    }
}
