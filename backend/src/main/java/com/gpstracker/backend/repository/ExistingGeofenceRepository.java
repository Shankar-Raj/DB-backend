package com.gpstracker.backend.repository;

import com.gpstracker.backend.entity.ExistingGeofence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExistingGeofenceRepository
        extends JpaRepository<ExistingGeofence, Long> {

    @Query(value = """
    SELECT
        id,
        name,
        ST_AsGeoJSON(ST_GeomFromText(area)) AS geojson
    FROM tc_geofences
""", nativeQuery = true)
    List<Object[]> findAllRawGeofences();
}