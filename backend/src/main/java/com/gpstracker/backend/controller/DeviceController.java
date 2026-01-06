package com.gpstracker.backend.controller;

import com.gpstracker.backend.dto.DeviceLatestEventDTO;
import com.gpstracker.backend.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@CrossOrigin(origins = "http://localhost:3000")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/latest-event")
    public List<DeviceLatestEventDTO> getDevicesWithLatestEvent() {
        return deviceService.getDevicesWithLatestEvent();
    }
}
