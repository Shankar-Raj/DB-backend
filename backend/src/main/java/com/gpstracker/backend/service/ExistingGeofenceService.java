package com.gpstracker.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpstracker.backend.repository.ExistingGeofenceRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExistingGeofenceService {

    private final ExistingGeofenceRepository repository;
    private final ObjectMapper objectMapper;

    public ExistingGeofenceService(ExistingGeofenceRepository repository,
                                   ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public List<Map<String, Object>> getAllGeofences() {

        List<Object[]> rows = repository.findAllRawGeofences();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : rows) {

            Long id = ((Number) row[0]).longValue();
            String name = (String) row[1];
            String geoJsonString = (String) row[2];

            try {

                JsonNode geoNode = objectMapper.readTree(geoJsonString);

                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("name", name);
                map.put("type", geoNode.get("type").asText());

                //  THIS IS THE IMPORTANT FIX
                map.put("coordinates",
                        objectMapper.convertValue(
                                geoNode.get("coordinates"),
                                Object.class
                        )
                );

                result.add(map);

            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }

        return result;
    }
}