package com.gpstracker.backend.repository;

import com.gpstracker.backend.dto.DeviceLatestEventDTO;
import com.gpstracker.backend.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query(value = """
        SELECT
            td.id AS deviceId,
            td.name AS name,
            td.uniqueid AS uniqueid,
            CASE
                WHEN le.id IS NULL THEN 'no data'
                WHEN le.type = 'deviceMoving' THEN 'running'
                WHEN le.type = 'deviceStopped' THEN 'idle'
                WHEN le.type IN ('ignitionOff', 'deviceOnline') THEN 'stopped'
                WHEN le.type IN ('deviceUnknown', 'deviceOffline') THEN 'inactive'
                ELSE 'unknown'
            END AS status
        FROM tc_devices td
        LEFT JOIN tc_events le
            ON le.id = (
                SELECT MAX(e.id)
                FROM tc_events e
                WHERE e.deviceid = td.id
            )
        ORDER BY td.id
        """, nativeQuery = true)
    List<DeviceLatestEventDTO> findDevicesWithLatestEvent();
}
