package com.gpstracker.backend.service;

import com.gpstracker.backend.entity.deviceLiveDetails;
import com.gpstracker.backend.repository.deviceLiveDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class deviceLiveDetailsService {

    private final deviceLiveDetailsRepository repository;

    public deviceLiveDetailsService(deviceLiveDetailsRepository repository) {
        this.repository = repository;
    }

    public List<deviceLiveDetails> getAllLiveDevices() {
        return repository.findAll();
    }
}

