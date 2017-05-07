package com.dev.domain.dao;

import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.doctor.Doctor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NetworkModelRepository extends CrudRepository<NetworkModel, Long> {
    List<NetworkModel> findAllByOwner(Doctor owner);
}
