package com.dev.service;

import com.dev.domain.DTO.DoctorDTO;
import com.dev.domain.model.doctor.Doctor;

public interface DoctorService {
    Doctor getDoctor(String login);

    Doctor getCurrentDoctor();

    Doctor findByLogin(String login);

    Doctor findById(long id);

    void save(Doctor doctor);

    void save(DoctorDTO doctorDTO);
}
