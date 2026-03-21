package com.gpstracker.backend.controller;

import com.gpstracker.backend.service.ExistingGeofenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/existing-geofences")
@CrossOrigin(origins = "http://localhost:3000")
public class ExistingGeofenceController {

    private final ExistingGeofenceService service;

    public ExistingGeofenceController(ExistingGeofenceService service) {
        this.service = service;
    }

    @GetMapping
    public List<Map<String, Object>> getAllGeofences() {
        return service.getAllGeofences();
    }
}