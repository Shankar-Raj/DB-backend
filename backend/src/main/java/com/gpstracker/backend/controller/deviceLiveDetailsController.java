package com.gpstracker.backend.controller;

import com.gpstracker.backend.entity.deviceLiveDetails;
import com.gpstracker.backend.service.deviceLiveDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/devices/live-details")
@CrossOrigin(origins = "http://localhost:3000")
public class deviceLiveDetailsController {

    private final deviceLiveDetailsService service;

    public deviceLiveDetailsController(deviceLiveDetailsService service) {
        this.service = service;
    }

    @GetMapping
    public List<deviceLiveDetails> getLiveDevices() {
        return service.getAllLiveDevices();
    }
}
