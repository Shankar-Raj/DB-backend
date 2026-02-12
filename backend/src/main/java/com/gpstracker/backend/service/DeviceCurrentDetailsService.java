package com.gpstracker.backend.service;

import com.gpstracker.backend.dto.DeviceCurrentDetailsDTO;
import com.gpstracker.backend.repository.DeviceCurrentDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceCurrentDetailsService {

    private final DeviceCurrentDetailsRepository repository;

    public DeviceCurrentDetailsService(DeviceCurrentDetailsRepository repository) {

        this.repository = repository;
    }

    public List<DeviceCurrentDetailsDTO> getAllLiveDevices() {
        return repository.findAllLiveDevices();
    }


}
