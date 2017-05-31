package com.dev.service.impl;

import com.dev.domain.model.DTO.DoctorDTO;
import com.dev.domain.model.doctor.Doctor;
import com.dev.domain.repository.DoctorRepository;
import com.dev.domain.repository.RoleRepository;
import com.dev.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, RoleRepository roleRepository) {
        this.doctorRepository = doctorRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Doctor getDoctor(String login) {
        return doctorRepository.findOneByLogin(login);
    }

    @Override
    public Doctor getCurrentDoctor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return doctorRepository.findOneByLogin(authentication.getName());
        }
        return null;
    }

    @Override
    public Doctor findByLogin(String login) {
        return doctorRepository.findOneByLogin(login);
    }

    @Override
    public Doctor findById(long id) {
        return doctorRepository.findOne(id);
    }

    @Override
    public void save(Doctor doctor) {
        doctor.setPassword(bCryptPasswordEncoder.encode(doctor.getPassword()));
        doctor.setRoles(new ArrayList<>(roleRepository.findAll()));
        doctor.setEnabled(true);
        doctorRepository.save(doctor);
    }

    @Override
    public void save(DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.findOne(doctorDTO.getId());
        doctor.setLogin(doctorDTO.getLogin());
        doctor.setName(doctorDTO.getName());
        doctor.setSurname(doctorDTO.getSurname());
        doctor.setMiddleName(doctorDTO.getMiddleName());
        doctor.setPosition(doctorDTO.getPosition());
        doctor.setEmail(doctorDTO.getEmail());
        doctorRepository.save(doctor);
    }
}
