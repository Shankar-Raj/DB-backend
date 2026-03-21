package com.gpstracker.backend.repository;

import com.gpstracker.backend.entity.GeofenceZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeofenceZoneRepository extends JpaRepository<GeofenceZone, Long> {

    // ── Existing native queries (kept as-is) ──────────────

    @Modifying
    @Query(value = """
        INSERT INTO geofence_zone (geofence_id, name, type, geometry)
        VALUES (:gfId, :name, :type, ST_GeomFromText(:wkt))
        """, nativeQuery = true)
    void insertZone(@Param("gfId")  Long geofenceId,
                    @Param("name")  String name,
                    @Param("type")  String type,
                    @Param("wkt")   String wkt);

    @Modifying
    @Query(value = """
        INSERT INTO geofence_zone (geofence_id, name, type, geometry, radius)
        VALUES (:gfId, :name, 'Circle', ST_GeomFromText(:wkt), :radius)
        """, nativeQuery = true)
    void insertCircle(@Param("gfId")   Long geofenceId,
                      @Param("name")   String name,
                      @Param("wkt")    String centerWkt,
                      @Param("radius") Double radius);

    @Query(value = """
        SELECT gz.id, gz.name, gz.type, gz.radius,
               ST_AsText(gz.geometry) AS geometry_wkt
        FROM geofence_zone gz
        WHERE gz.geofence_id = :geofenceId
        ORDER BY gz.id
        """, nativeQuery = true)
    List<Object[]> findZonesByGeofenceId(@Param("geofenceId") Long geofenceId);

    @Modifying
    @Query(value = "DELETE FROM geofence_zone WHERE geofence_id = :geofenceId",
            nativeQuery = true)
    void deleteByGeofenceId(@Param("geofenceId") Long geofenceId);

    // ── NEW: update geometry for an existing zone ─────────
    @Modifying
    @Query(value = """
        UPDATE geofence_zone
        SET name = :name, type = :type, geometry = ST_GeomFromText(:wkt), radius = NULL
        WHERE id = :id
        """, nativeQuery = true)
    void updateZoneGeometry(@Param("id")   Long id,
                            @Param("name") String name,
                            @Param("type") String type,
                            @Param("wkt")  String wkt);

    @Modifying
    @Query(value = """
        UPDATE geofence_zone
        SET name = :name, type = 'Circle', geometry = ST_GeomFromText(:wkt), radius = :radius
        WHERE id = :id
        """, nativeQuery = true)
    void updateCircleGeometry(@Param("id")     Long id,
                              @Param("name")   String name,
                              @Param("wkt")    String centerWkt,
                              @Param("radius") Double radius);

    @Modifying
    @Query(value = "DELETE FROM geofence_zone WHERE id = :id", nativeQuery = true)
    void deleteZoneById(@Param("id") Long id);

    // ── Spatial queries (unchanged) ───────────────────────

    @Query(value = """
        SELECT gz.geofence_id FROM geofence_zone gz
        WHERE gz.type = 'Polygon'
          AND ST_Contains(gz.geometry, ST_GeomFromText(:pointWkt))
        """, nativeQuery = true)
    List<Long> findPolygonsContaining(@Param("pointWkt") String pointWkt);

    @Query(value = """
        SELECT gz.geofence_id FROM geofence_zone gz
        WHERE gz.type = 'Circle'
          AND ST_Distance_Sphere(gz.geometry, ST_GeomFromText(:pointWkt)) <= gz.radius
        """, nativeQuery = true)
    List<Long> findCirclesContaining(@Param("pointWkt") String pointWkt);
}