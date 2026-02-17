package com.gpstracker.backend.service;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gpstracker.backend.dto.DeviceLivePatchDTO;
import com.gpstracker.backend.repository.PositionLiveRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PositionPollingService {

    private final PositionLiveRepository repository;
    private final ReverseGeocodeService reverseGeocodeService;
    private final LivePatchPublisher publisher;
    private final JdbcTemplate jdbcTemplate;

    private Long lastProcessedId;

    public PositionPollingService(
            PositionLiveRepository repository,
            ReverseGeocodeService reverseGeocodeService,
            LivePatchPublisher publisher,
            JdbcTemplate jdbcTemplate
    ) {
        this.repository = repository;
        this.reverseGeocodeService = reverseGeocodeService;
        this.publisher = publisher;
        this.jdbcTemplate = jdbcTemplate;

        System.out.println("PositionPollingService bean created");
    }

    /**
     * Start polling from the first record.
     */
    @PostConstruct
    public void init() {

        Long minId = jdbcTemplate.queryForObject(
                "SELECT COALESCE(MIN(id), 0) FROM tc_positions",
                Long.class
        );

        lastProcessedId = (minId != null ? minId : 0) - 1;

        System.out.println("Live tracking started from ID: " + lastProcessedId);
    }

    /**
     * Poll database every 5 seconds
     */
    @Scheduled(fixedDelay = 5000)
    public void poll() {

        try {

            System.out.println("Polling with lastProcessedId = " + lastProcessedId);

            List<Object[]> rows =
                    repository.findNewLivePositions(lastProcessedId);

            System.out.println("Rows found = " + rows.size());

            if (rows.isEmpty()) {
                return;
            }

            Long highestId = lastProcessedId;

            for (Object[] row : rows) {

                Long id = ((Number) row[0]).longValue();
                Long deviceId = ((Number) row[1]).longValue();
                Double latitude = row[2] != null ? ((Number) row[2]).doubleValue() : null;
                Double longitude = row[3] != null ? ((Number) row[3]).doubleValue() : null;
                Double speed = row[4] != null ? ((Number) row[4]).doubleValue() : 0.0;

                // FIXED TIMESTAMP ISSUE
                LocalDateTime fixTime = null;

                if (row[5] instanceof LocalDateTime ldt) {
                    fixTime = ldt;
                } else if (row[5] instanceof java.sql.Timestamp ts) {
                    fixTime = ts.toLocalDateTime();
                }

                Integer course = row[6] != null ? ((Number) row[6]).intValue() : null;
                String eventType = (String) row[7];

                String locationName = "test-location";

                /*
                // Enable if needed
                String locationName = reverseGeocodeService
                        .getLocationName(latitude, longitude);
                */

                DeviceLivePatchDTO dto = new DeviceLivePatchDTO(
                        deviceId,
                        latitude,
                        longitude,
                        speed,
                        course,
                        fixTime,
                        eventType,
                        locationName
                );

                publisher.publish(dto);

                if (id > highestId) {
                    highestId = id;
                }
            }

            // Update pointer AFTER successful processing
            lastProcessedId = highestId;

            System.out.println("Updated lastProcessedId to = " + lastProcessedId);

        } catch (Exception e) {

            System.out.println("ERROR INSIDE POLL");
            e.fillInStackTrace();   // Proper stack trace
        }
    }
}
