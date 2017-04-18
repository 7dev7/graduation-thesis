package com.dev.service;

import com.dev.domain.dao.DoctorRepository;
import com.dev.domain.dao.RoleRepository;
import com.dev.domain.model.user.Doctor;
import com.dev.domain.model.user.Role;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final RoleRepository roleRepository;
    private final RoleManager roleManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Value("${password.size}")
    private int passwordSize;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, RoleRepository roleRepository, RoleManager roleManager) {
        this.doctorRepository = doctorRepository;
        this.roleRepository = roleRepository;
        this.roleManager = roleManager;
    }

    private String getCurrentDoctorLogin() {
        Doctor currentDoctor = getCurrentDoctor();
        return currentDoctor != null ? currentDoctor.getLogin() : null;
    }

    public Doctor getDoctor(String login) {
        return doctorRepository.findOneByLogin(login);
    }

    public Doctor getCurrentDoctor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return doctorRepository.findOneByLogin(authentication.getName());
        }
        return null;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public boolean addDoctor(String login, String pass, List<String> rolesName, String email) {
        if (doctorRepository.findOneByLogin(login) != null) {
            return false;
        }
        Doctor doctor = new Doctor();
        doctor.setLogin(login);
        doctor.setEmail(email);
        doctor.setEnabled(true);
        doctor.setPassword(bCryptPasswordEncoder.encode(pass));
        doctor.setRoles(getRolesByAuthorityNames(rolesName));
        doctorRepository.save(doctor);
        return true;
    }

    private List<Role> getRolesByAuthorityNames(List<String> authorityNames) {
        List<Role> rolesResult = new ArrayList<>();
        if (authorityNames == null) {
            return rolesResult;
        }
        for (String authority : authorityNames) {
            Role role = roleRepository.findByAuthority(authority);
            if (role != null) {
                rolesResult.add(role);
            }
        }
        return rolesResult;
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphabetic(passwordSize);
    }

    private String generateLogin(String name, String lastName) {
        Calendar calendar = Calendar.getInstance();
        return name.trim().substring(0, 2) + lastName.trim().substring(0, 2) + calendar.get(Calendar.DAY_OF_MONTH) + calendar.get(Calendar.YEAR);
    }

    public Doctor findByLogin(String login) {
        return doctorRepository.findOneByLogin(login);
    }

    public void save(Doctor doctor) {
        doctor.setPassword(bCryptPasswordEncoder.encode(doctor.getPassword()));
        doctor.setRoles(new ArrayList<>(roleRepository.findAll()));
        doctor.setEnabled(true);
        doctorRepository.save(doctor);
    }
}
