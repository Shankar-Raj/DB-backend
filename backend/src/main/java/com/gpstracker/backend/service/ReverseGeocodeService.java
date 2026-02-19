package com.gpstracker.backend.service;

import org.springframework.core.ParameterizedTypeReference;
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

        String key = String.format("%.3f,%.3f", lat, lon);

        // Use computeIfAbsent (cleaner & thread-safe)
        return cache.computeIfAbsent(key, k -> fetchLocation(lat, lon));
    }

    private String fetchLocation(Double lat, Double lon) {

        try {
            String url = "https://nominatim.openstreetmap.org/reverse" +
                    "?format=json&lat=" + lat +
                    "&lon=" + lon;

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "gpstracker-app");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map<String, Object>> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            new ParameterizedTypeReference<>() {}
                    );

            Map<String, Object> body = response.getBody();
            if (body == null) return "can't fetch location";

            Object addressObj = body.get("address");
            if (!(addressObj instanceof Map)) return "can't fetch location";

            @SuppressWarnings("unchecked")
            Map<String, Object> address =
                    (Map<String, Object>) addressObj;

            String city = getString(address, "city",
                    getString(address, "town",
                            getString(address, "village", "")));

            String state = getString(address, "state", "");

            if (!city.isEmpty()) {
                if (city.length() < 13 && !state.isEmpty()) {
                    return city + ", " + state;
                }
                return city.substring(0, 10) + "..";
            } else {
                return state;
            }

        } catch (Exception e) {
            return "can't fetch location";
        }
    }

    private String getString(Map<String, Object> map,
                             String key,
                             String defaultValue) {

        Object value = map.get(key);
        return value != null ? value.toString() : defaultValue;
    }
}
