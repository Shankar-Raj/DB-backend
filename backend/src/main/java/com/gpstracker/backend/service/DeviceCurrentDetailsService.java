package com.gpstracker.backend.service;

import com.gpstracker.backend.dto.DeviceCurrentDetailsDTO;
import com.gpstracker.backend.repository.DeviceCurrentDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceCurrentDetailsService {

    private final DeviceCurrentDetailsRepository repository;
    private final ReverseGeocodeService reverseGeocodeService;

    public DeviceCurrentDetailsService(
            DeviceCurrentDetailsRepository repository,
            ReverseGeocodeService reverseGeocodeService) {

        this.repository = repository;
        this.reverseGeocodeService = reverseGeocodeService;
    }

    public List<DeviceCurrentDetailsDTO> getAllLiveDevices() {

        List<DeviceCurrentDetailsDTO> devices = repository.findAllLiveDevices();

        for (DeviceCurrentDetailsDTO device : devices) {

            if (device.latitude != null && device.longitude != null) {

                device.locationName = reverseGeocodeService
                        .getLocationName(device.latitude, device.longitude);
            } else {
                device.locationName = "Unknown Location";
            }
        }

        return devices;
    }
}
