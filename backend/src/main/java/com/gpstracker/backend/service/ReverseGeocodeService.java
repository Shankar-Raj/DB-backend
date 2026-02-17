package com.gpstracker.backend.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReverseGeocodeService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, String> cache = new ConcurrentHashMap<>();

    public String getLocationName(Double lat, Double lon) {

        if (lat == null || lon == null) return "";

        String key = String.format("%.3f,%.3f", lat, lon);

        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        try {
            String url = "https://nominatim.openstreetmap.org/reverse" +
                    "?format=json&lat=" + lat +
                    "&lon=" + lon;

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "gpstracker-app");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            Map address = (Map) response.getBody().get("address");

            String city = (String) address.getOrDefault("city",
                    address.getOrDefault("town",
                            address.getOrDefault("village", "")));

            String state = (String) address.getOrDefault("state", "");

            String location = city + ", " + state;

            cache.put(key, location);

            return location;

        } catch (Exception e) {
            return "";
        }
    }
}

