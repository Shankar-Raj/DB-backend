package com.gpstracker.backend.controller;

import com.gpstracker.backend.dto.GeofenceDTO;
import com.gpstracker.backend.service.GeofenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/geofences")
public class GeofenceController {

    private final GeofenceService service;

    public GeofenceController(GeofenceService service) {
        this.service = service;
    }

    @GetMapping
    public List<Map<String, Object>> getAllGeofences() {
        return service.getAllGeofences();
    }
}