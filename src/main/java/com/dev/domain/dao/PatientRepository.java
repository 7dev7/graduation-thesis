package com.dev.domain.dao;

import com.dev.domain.model.Patient;
import com.dev.domain.model.doctor.Doctor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, Long> {
    List<Patient> findPatientsByDoctor(Doctor doctor);
}
