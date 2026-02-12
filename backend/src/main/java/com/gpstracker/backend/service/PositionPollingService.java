package com.gpstracker.backend.service;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gpstracker.backend.dto.DeviceLivePatchDTO;
import com.gpstracker.backend.repository.PositionLiveRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PositionPollingService {

    private final PositionLiveRepository repository;
    private final LivePatchPublisher publisher;
    private final JdbcTemplate jdbcTemplate;

    private volatile boolean liveEnabled = false;
    private Long lastProcessedId = 0L;

    public PositionPollingService(
            PositionLiveRepository repository,
            LivePatchPublisher publisher,
            JdbcTemplate jdbcTemplate
    ) {
        this.repository = repository;
        this.publisher = publisher;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        Long maxId = jdbcTemplate.queryForObject(
                "SELECT COALESCE(MAX(id),0) FROM tc_positions",
                Long.class
        );
        lastProcessedId = maxId != null ? maxId : 0L;
    }

    public void start() {
        liveEnabled = true;
    }

    public void stop() {
        liveEnabled = false;
    }

    @Scheduled(fixedDelay = 2000)
    public void poll() {

        if (!liveEnabled) return;

        List<Object[]> rows =
                repository.findNewLivePositions(lastProcessedId);

        for (Object[] row : rows) {

            Long id = ((Number) row[0]).longValue();
            Long deviceId = ((Number) row[1]).longValue();
            Double latitude = (Double) row[2];
            Double longitude = (Double) row[3];
            Double speed = row[4] != null
                    ? ((Number) row[4]).doubleValue()
                    : null;
            LocalDateTime fixtime =
                    ((Timestamp) row[5]).toLocalDateTime();
            Integer course = row[6] != null
                    ? ((Number) row[6]).intValue()
                    : null;
            String eventType = (String) row[7];

            DeviceLivePatchDTO patch =
                    new DeviceLivePatchDTO(
                            deviceId,
                            latitude,
                            longitude,
                            speed,
                            course,
                            fixtime,
                            eventType
                    );

            publisher.publish(patch);

            lastProcessedId = id;
        }
    }
}
