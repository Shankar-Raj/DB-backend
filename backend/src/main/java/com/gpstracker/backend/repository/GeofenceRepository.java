package com.gpstracker.backend.repository;

import com.gpstracker.backend.entity.Geofence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeofenceRepository
        extends JpaRepository<Geofence, Long> {

    @Query(value = """
    SELECT
        id,
        name,
        ST_AsGeoJSON(ST_GeomFromText(area)) AS geojson
    FROM tc_geofences
""", nativeQuery = true)
    List<Object[]> findAllRawGeofences();
}