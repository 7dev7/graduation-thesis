package com.dev;

import com.dev.domain.dao.DoctorRepository;
import com.dev.domain.dao.PatientRepository;
import com.dev.domain.dao.RoleRepository;
import com.dev.domain.dao.UserRepository;
import com.dev.domain.model.user.Role;
import com.dev.domain.model.user.User;
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

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleManager roleManager;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public BaseConfig(UserRepository userRepository, RoleRepository roleRepository,
                      RoleManager roleManager, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleManager = roleManager;
        this.doctorRepository = doctorRepository;
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
        User user = new User();
        user.setLogin("user");
        user.setPassword(new BCryptPasswordEncoder().encode("user"));
        user.setEmail("us.us@us.com");
        user.setEnabled(true);
        user.setRoles(Arrays.asList(roleManager.getRoleByRoleName("DOCTOR")));
        userRepository.save(user);


//        Doctor doctor = new Doctor();
//        doctor.setLogin("doc");
//        doctor.setPassword(new BCryptPasswordEncoder().encode("doc"));
//        doctor.setEmail("dc.dc@us.com");
//        doctor.setEnabled(true);
//        doctor.setRoles(Arrays.asList(roleManager.getRoleByRoleName("DOCTOR")));
//
//
//        Patient patient = new Patient();
//        patient.setLogin("pat");
//        patient.setPassword(new BCryptPasswordEncoder().encode("pat"));
//        patient.setEmail("pat.pat@us.com");
//        patient.setEnabled(true);
//        patient.setRoles(Arrays.asList(roleManager.getRoleByRoleName("PATIENT")));
//        patient.setDoctor(doctor);
//
//        doctor.setPatients(Arrays.asList(patient));
//
//        userRepository.save(patient);
//        userRepository.save(doctor);
    }
}
