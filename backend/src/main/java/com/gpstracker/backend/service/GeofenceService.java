package com.gpstracker.backend.service;

import com.gpstracker.backend.dto.GeofenceDTO;
import com.gpstracker.backend.dto.ZoneDTO;
import com.gpstracker.backend.entity.Geofence;
import com.gpstracker.backend.repository.GeofenceRepository;
import com.gpstracker.backend.repository.GeofenceZoneRepository;
import com.gpstracker.backend.util.WktUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class GeofenceService {

    private final GeofenceRepository     gfRepo;
    private final GeofenceZoneRepository zoneRepo;

    public GeofenceService(GeofenceRepository gfRepo,
                           GeofenceZoneRepository zoneRepo) {
        this.gfRepo   = gfRepo;
        this.zoneRepo = zoneRepo;
    }

    public List<GeofenceDTO> getAll() {
        return gfRepo.findAll().stream()
                .map(gf -> buildDTO(gf, zoneRepo.findZonesByGeofenceId(gf.getId())))
                .collect(Collectors.toList());
    }

    public GeofenceDTO getOne(Long id) {
        return buildDTO(require(id), zoneRepo.findZonesByGeofenceId(id));
    }

    public GeofenceDTO create(GeofenceDTO dto) {
        Geofence gf = new Geofence();
        gf.setName(dto.getName());
        gf = gfRepo.save(gf);
        insertAllZones(gf.getId(), dto.getGeofences());
        return buildDTO(gf, zoneRepo.findZonesByGeofenceId(gf.getId()));
    }

    // ── FIXED UPDATE — merge zones in place ──────────────
    public GeofenceDTO update(Long id, GeofenceDTO dto) {
        Geofence gf = require(id);
        gf.setName(dto.getName());
        gfRepo.save(gf);                          // UPDATE geofence row
        mergeZones(id, dto.getGeofences());       // merge zones in place
        return buildDTO(gf, zoneRepo.findZonesByGeofenceId(id));
    }

    public void delete(Long id) {
        gfRepo.deleteById(id);   // CASCADE removes zones
    }

    public List<Long> getGeofencesContaining(double lat, double lng) {
        String wkt = WktUtils.toPointWkt(List.of(lat, lng));
        List<Long> ids = new ArrayList<>();
        ids.addAll(zoneRepo.findPolygonsContaining(wkt));
        ids.addAll(zoneRepo.findCirclesContaining(wkt));
        return ids.stream().distinct().collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────────────
    // Merge: UPDATE existing, INSERT new, DELETE removed
    // ─────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    private void mergeZones(Long geofenceId, List<ZoneDTO> dtos) {
        if (dtos == null) dtos = List.of();

        // Existing zone ids in DB
        List<Object[]> existing = zoneRepo.findZonesByGeofenceId(geofenceId);
        Set<Long> existingIds   = existing.stream()
                .map(row -> ((Number) row[0]).longValue())
                .collect(Collectors.toSet());

        Set<Long> keptIds = new HashSet<>();

        for (ZoneDTO z : dtos) {
            String name = z.getName() != null ? z.getName() : z.getType();
            Long zoneId = z.getId() != null ? parseLong(z.getId()) : null;
            boolean isExisting = zoneId != null && existingIds.contains(zoneId);

            if (isExisting) {
                // ── UPDATE existing zone geometry in place ──
                switch (z.getType()) {
                    case "Polygon" -> {
                        List<List<Double>> c = (List<List<Double>>) z.getCoordinates();
                        zoneRepo.updateZoneGeometry(zoneId, name, "Polygon",
                                WktUtils.toPolygonWkt(c));
                    }
                    case "LineString" -> {
                        List<List<Double>> c = (List<List<Double>>) z.getCoordinates();
                        zoneRepo.updateZoneGeometry(zoneId, name, "LineString",
                                WktUtils.toLineStringWkt(c));
                    }
                    case "Point" -> {
                        List<Double> latLng = (List<Double>) z.getCoordinates();
                        zoneRepo.updateZoneGeometry(zoneId, name, "Point",
                                WktUtils.toPointWkt(latLng));
                    }
                    case "Circle" -> {
                        zoneRepo.updateCircleGeometry(zoneId, name,
                                WktUtils.toPointWkt(z.getCenter()), z.getRadius());
                    }
                }
                keptIds.add(zoneId);
            } else {
                // ── INSERT new zone ──
                switch (z.getType()) {
                    case "Polygon" -> {
                        List<List<Double>> c = (List<List<Double>>) z.getCoordinates();
                        zoneRepo.insertZone(geofenceId, name, "Polygon",
                                WktUtils.toPolygonWkt(c));
                    }
                    case "LineString" -> {
                        List<List<Double>> c = (List<List<Double>>) z.getCoordinates();
                        zoneRepo.insertZone(geofenceId, name, "LineString",
                                WktUtils.toLineStringWkt(c));
                    }
                    case "Point" -> {
                        List<Double> latLng = (List<Double>) z.getCoordinates();
                        zoneRepo.insertZone(geofenceId, name, "Point",
                                WktUtils.toPointWkt(latLng));
                    }
                    case "Circle" -> {
                        zoneRepo.insertCircle(geofenceId, name,
                                WktUtils.toPointWkt(z.getCenter()), z.getRadius());
                    }
                }
                // Note: new zone id is auto-generated; we don't track it for keptIds
                // since it's new — it will never be in the "existing to delete" set
            }
        }

        // ── DELETE removed zones ──
        for (Long existingId : existingIds) {
            if (!keptIds.contains(existingId)) {
                zoneRepo.deleteZoneById(existingId);
            }
        }
    }

    // ── Used only by create ───────────────────────────────
    @SuppressWarnings("unchecked")
    private void insertAllZones(Long geofenceId, List<ZoneDTO> dtos) {
        if (dtos == null) return;
        for (ZoneDTO z : dtos) {
            String name = z.getName() != null ? z.getName() : z.getType();
            switch (z.getType()) {
                case "Polygon" -> {
                    List<List<Double>> c = (List<List<Double>>) z.getCoordinates();
                    zoneRepo.insertZone(geofenceId, name, "Polygon", WktUtils.toPolygonWkt(c));
                }
                case "LineString" -> {
                    List<List<Double>> c = (List<List<Double>>) z.getCoordinates();
                    zoneRepo.insertZone(geofenceId, name, "LineString", WktUtils.toLineStringWkt(c));
                }
                case "Point" -> {
                    List<Double> ll = (List<Double>) z.getCoordinates();
                    zoneRepo.insertZone(geofenceId, name, "Point", WktUtils.toPointWkt(ll));
                }
                case "Circle" -> {
                    zoneRepo.insertCircle(geofenceId, name,
                            WktUtils.toPointWkt(z.getCenter()), z.getRadius());
                }
            }
        }
    }

    private GeofenceDTO buildDTO(Geofence gf, List<Object[]> rows) {
        GeofenceDTO dto = new GeofenceDTO();
        dto.setId(gf.getId());
        dto.setName(gf.getName());

        List<ZoneDTO> zones = new ArrayList<>();
        for (Object[] row : rows) {
            // row: [0]=id, [1]=name, [2]=type, [3]=radius, [4]=geometry_wkt
            ZoneDTO zd  = new ZoneDTO();
            zd.setId(String.valueOf(row[0]));   // ← always return zone id
            zd.setName((String) row[1]);
            String type = (String) row[2];
            zd.setType(type);
            String wkt  = (String) row[4];

            switch (type) {
                case "Polygon"    -> zd.setCoordinates(WktUtils.fromPolygonWkt(wkt));
                case "LineString" -> zd.setCoordinates(WktUtils.fromLineStringWkt(wkt));
                case "Point"      -> zd.setCoordinates(WktUtils.fromPointWkt(wkt));
                case "Circle"     -> {
                    zd.setCenter(WktUtils.fromPointWkt(wkt));
                    zd.setRadius(row[3] != null ? ((Number) row[3]).doubleValue() : null);
                }
            }
            zones.add(zd);
        }
        dto.setGeofences(zones);
        return dto;
    }

    private Long parseLong(String s) {
        try { return Long.parseLong(s); } catch (Exception e) { return null; }
    }

    private Geofence require(Long id) {
        return gfRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Geofence not found: " + id));
    }
}