package com.dev;

import com.dev.domain.model.doctor.Doctor;
import com.dev.domain.model.doctor.Role;
import com.dev.domain.repository.DoctorRepository;
import com.dev.domain.repository.RoleRepository;
import com.dev.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class BaseConfig {
    private static final Map<String, Role> ROLES = new HashMap<>();

    static {
        Role role1 = new Role();
        role1.setAuthority("ROLE_DOCTOR");
        ROLES.put("DOCTOR", role1);
        Role role2 = new Role();
        role2.setAuthority("ROLE_PATIENT");
        ROLES.put("PATIENT", role2);
    }

    private final DoctorRepository doctorRepository;
    private final RoleRepository roleRepository;
    private final RoleServiceImpl roleManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public BaseConfig(DoctorRepository doctorRepository, RoleRepository roleRepository,
                      RoleServiceImpl roleManager) {
        this.doctorRepository = doctorRepository;
        this.roleRepository = roleRepository;
        this.roleManager = roleManager;
    }

    @PostConstruct
    public void init() {
        initData();
    }

    private void initData() {
        roleRepository.save(ROLES.values());
        initUsers();
    }

    private void initUsers() {
        Doctor doctor = new Doctor();
        doctor.setLogin("doctor");
        doctor.setName("Иван");
        doctor.setSurname("Иванов");
        doctor.setMiddleName("Иванович");
        doctor.setPosition("Генетик");
        doctor.setPassword(bCryptPasswordEncoder.encode("doctor"));
        doctor.setPasswordConfirm(bCryptPasswordEncoder.encode("doctor"));
        doctor.setEmail("us.us@us.com");
        doctor.setEnabled(true);
        doctor.setRoles(Arrays.asList(roleManager.getRoleByRoleName("DOCTOR")));
        doctorRepository.save(doctor);
    }
}
