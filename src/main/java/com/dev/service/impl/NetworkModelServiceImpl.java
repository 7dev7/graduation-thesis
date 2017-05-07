package com.dev.service.impl;

import com.dev.domain.dao.NetworkModelRepository;
import com.dev.domain.model.NetworkModel;
import com.dev.service.DoctorService;
import com.dev.service.NetworkModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetworkModelServiceImpl implements NetworkModelService {
    private final NetworkModelRepository networkModelRepository;
    private final DoctorService doctorService;

    @Autowired
    public NetworkModelServiceImpl(NetworkModelRepository networkModelRepository, DoctorService doctorService) {
        this.networkModelRepository = networkModelRepository;
        this.doctorService = doctorService;
    }

    @Override
    public void save(NetworkModel networkModel) {
        networkModel.setOwner(doctorService.getCurrentDoctor());
        networkModelRepository.save(networkModel);
    }

    @Override
    public List<NetworkModel> getModelsForCurrentDoctor() {
        return networkModelRepository.findAllByOwner(doctorService.getCurrentDoctor());
    }
}