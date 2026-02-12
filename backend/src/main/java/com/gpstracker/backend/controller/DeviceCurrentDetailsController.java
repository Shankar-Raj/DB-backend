package com.gpstracker.backend.controller;

import com.gpstracker.backend.dto.DeviceCurrentDetailsDTO;
import com.gpstracker.backend.service.DeviceCurrentDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices/")
@CrossOrigin(origins = "http://localhost:3000")
public class DeviceCurrentDetailsController {

    private final DeviceCurrentDetailsService service;

    public DeviceCurrentDetailsController(DeviceCurrentDetailsService service) {
        this.service = service;
    }

    @GetMapping("/live-details")
    public List<DeviceCurrentDetailsDTO> getAllLiveDevices() {
        return service.getAllLiveDevices();
    }
}
