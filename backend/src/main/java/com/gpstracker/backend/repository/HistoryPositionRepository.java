package com.gpstracker.backend.repository;

import com.gpstracker.backend.dto.PositionHistoryDTO;
import com.gpstracker.backend.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryPositionRepository
        extends JpaRepository<Position, Long> {

    @Query("""
        SELECT new com.gpstracker.backend.dto.PositionHistoryDTO(
            p.latitude,
            p.longitude,
            p.speed,
            p.fixtime,
            p.course
        )
        FROM Position p
        WHERE p.deviceId = :deviceId
          AND p.fixtime BETWEEN :from AND :to
        ORDER BY p.fixtime ASC
    """)
    List<PositionHistoryDTO> findHistory(
            @Param("deviceId") Long deviceId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}
