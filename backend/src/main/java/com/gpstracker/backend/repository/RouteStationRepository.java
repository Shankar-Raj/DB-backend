package com.gpstracker.backend.repository;

import com.gpstracker.backend.entity.RouteStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteStationRepository extends JpaRepository<RouteStation, Long> {

    List<RouteStation> findByRouteIdOrderBySerialAsc(Long routeId);

    // Still needed for full wipe on route delete (CASCADE handles it,
    // but kept for manual use if needed)
    @Modifying
    @Query("DELETE FROM RouteStation rs WHERE rs.routeId = :routeId")
    void deleteByRouteId(@Param("routeId") Long routeId);
}