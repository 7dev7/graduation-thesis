package com.dev.domain.dao;

import com.dev.domain.model.Doctor;
import com.dev.domain.model.Patient;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    Doctor findDoctorByPatients(Patient patient);
}
