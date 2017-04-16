package com.dev.domain.dao;

import com.dev.domain.model.Patient;
import com.dev.domain.model.user.Doctor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DoctorRepository extends CrudRepository<Doctor, String> {
    Doctor findOneByLogin(String login);

    @Override
    List<Doctor> findAll();

    Doctor findDoctorByPatients(Patient patient);
}
