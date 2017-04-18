package com.dev;

import com.dev.domain.dao.DoctorRepository;
import com.dev.domain.dao.PatientRepository;
import com.dev.domain.dao.RoleRepository;
import com.dev.domain.model.user.Doctor;
import com.dev.domain.model.user.Role;
import com.dev.service.RoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class BaseConfig {
    public static final Map<String, Role> ROLES = new HashMap<>();

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
    private final RoleManager roleManager;
    private final PatientRepository patientRepository;

    @Autowired
    public BaseConfig(DoctorRepository doctorRepository, RoleRepository roleRepository,
                      RoleManager roleManager, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.roleRepository = roleRepository;
        this.roleManager = roleManager;
        this.patientRepository = patientRepository;
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
        doctor.setPassword(new BCryptPasswordEncoder().encode("doctor"));
        doctor.setPasswordConfirm(new BCryptPasswordEncoder().encode("doctor"));
        doctor.setEmail("us.us@us.com");
        doctor.setEnabled(true);
        doctor.setRoles(Arrays.asList(roleManager.getRoleByRoleName("DOCTOR")));
        doctorRepository.save(doctor);
    }
}
