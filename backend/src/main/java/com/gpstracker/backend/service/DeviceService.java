package com.gpstracker.backend.service;

import com.gpstracker.backend.dto.DeviceLatestEventDTO;
import com.gpstracker.backend.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<DeviceLatestEventDTO> getDevicesWithLatestEvent() {
        return deviceRepository.findDevicesWithLatestEvent();
    }
}
