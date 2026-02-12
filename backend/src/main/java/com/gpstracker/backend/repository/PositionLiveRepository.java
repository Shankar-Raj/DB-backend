package com.gpstracker.backend.repository;

import com.gpstracker.backend.entity.Position;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PositionLiveRepository extends JpaRepository<Position, Long> {

    @Query(value = """
        SELECT
            p.id,
            p.deviceid,
            p.latitude,
            p.longitude,
            p.speed,
            p.fixtime,
            p.course,
            e.type
        FROM tc_positions p
        LEFT JOIN tc_events e
            ON p.id = e.positionid
        WHERE p.id > :lastId
        ORDER BY p.id ASC
        LIMIT 100
    """, nativeQuery = true)
    List<Object[]> findNewLivePositions(
            @Param("lastId") Long lastId
    );
}
