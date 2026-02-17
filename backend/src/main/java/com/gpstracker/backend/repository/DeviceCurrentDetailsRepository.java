package com.gpstracker.backend.repository;

import com.gpstracker.backend.dto.DeviceCurrentDetailsDTO;
import com.gpstracker.backend.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceCurrentDetailsRepository extends JpaRepository<Device, Long> {

    @Query("""
    SELECT new com.gpstracker.backend.dto.DeviceCurrentDetailsDTO(
        d.id,
        d.uniqueId,
        d.name,
        e.type,
        e.eventtime,
        p.speed,
        p.latitude,
        p.longitude,
        p.geofenceIds,
        p.course,
        p.fixtime,
        d.groupId,
        d.status
    )
    FROM Device d
    LEFT JOIN Event e
        ON e.id = (
            SELECT MIN(ev.id)
            FROM Event ev
            WHERE ev.deviceId = d.id
        )
    LEFT JOIN Position p
        ON p.id = e.positionId
    ORDER BY d.id
""")
    List<DeviceCurrentDetailsDTO> findAllLiveDevices();
}
