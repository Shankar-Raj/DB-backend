package com.gpstracker.backend.service;

import com.gpstracker.backend.dto.RouteDTO;
import com.gpstracker.backend.dto.RouteStationDTO;
import com.gpstracker.backend.entity.Route;
import com.gpstracker.backend.entity.RouteStation;
import com.gpstracker.backend.repository.RouteRepository;
import com.gpstracker.backend.repository.RouteStationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RouteService {

    private final RouteRepository        routeRepo;
    private final RouteStationRepository stationRepo;

    public RouteService(RouteRepository routeRepo,
                        RouteStationRepository stationRepo) {
        this.routeRepo   = routeRepo;
        this.stationRepo = stationRepo;
    }

    // ── GET ALL ──────────────────────────────────────────
    public List<RouteDTO> getAll() {
        return routeRepo.findAll().stream()
                .map(r -> toDTO(r, stationRepo.findByRouteIdOrderBySerialAsc(r.getId())))
                .collect(Collectors.toList());
    }

    // ── GET ONE ──────────────────────────────────────────
    public RouteDTO getOne(Long id) {
        Route r = require(id);
        return toDTO(r, stationRepo.findByRouteIdOrderBySerialAsc(id));
    }

    // ── CREATE ───────────────────────────────────────────
    public RouteDTO create(RouteDTO dto) {
        if (routeRepo.existsByRouteCode(dto.getRouteCode()))
            throw new RuntimeException("Route code already exists: " + dto.getRouteCode());
        Route r = new Route();
        applyMaster(r, dto);
        r = routeRepo.save(r);
        insertStations(r.getId(), dto.getStations());
        return toDTO(r, stationRepo.findByRouteIdOrderBySerialAsc(r.getId()));
    }

    // ── UPDATE ───────────────────────────────────────────
    // Route row → UPDATE in place (same id, same row)
    // Stations  → DELETE all then INSERT fresh (avoids serial unique conflict)
    public RouteDTO update(Long id, RouteDTO dto) {
        Route r = require(id);
        applyMaster(r, dto);
        routeRepo.save(r);                        // UPDATE route row in place

        stationRepo.deleteByRouteId(id);          // wipe old stations
        insertStations(id, dto.getStations());    // insert fresh with correct serials

        return toDTO(r, stationRepo.findByRouteIdOrderBySerialAsc(id));
    }

    // ── DELETE ───────────────────────────────────────────
    public void delete(Long id) {
        routeRepo.deleteById(id);   // CASCADE removes stations
    }

    // ─────────────────────────────────────────────────────
    // Private helpers
    // ─────────────────────────────────────────────────────

    private void insertStations(Long routeId, List<RouteStationDTO> dtos) {
        if (dtos == null) return;
        int serial = 1;
        for (RouteStationDTO dto : dtos) {
            RouteStation rs = new RouteStation();
            rs.setRouteId(routeId);
            rs.setSerial(serial++);
            rs.setStationName(dto.getStation());
            rs.setDistanceFromPrev(dto.getDistance());
            rs.setArrivalTime(parseTime(dto.getArrival()));
            rs.setDepartureTime(parseTime(dto.getDepart()));
            stationRepo.save(rs);
        }
    }

    private void applyMaster(Route r, RouteDTO dto) {
        r.setRouteCode(dto.getRouteCode());
        r.setRouteType(dto.getRouteType());
        r.setStartStation(dto.getStart());
        r.setEndStation(dto.getEnd());
        r.setStartTime(parseTime(dto.getStartTime()));
        r.setEndTime(parseTime(dto.getEndTime()));
        r.setTotalDistance(
                dto.getTotalDistance() != null && !dto.getTotalDistance().isBlank()
                        ? Double.parseDouble(dto.getTotalDistance()) : null);
    }

    private RouteDTO toDTO(Route r, List<RouteStation> stations) {
        RouteDTO dto = new RouteDTO();
        dto.setId(r.getId());
        dto.setRouteCode(r.getRouteCode());
        dto.setRouteType(r.getRouteType());
        dto.setStart(r.getStartStation());
        dto.setEnd(r.getEndStation());
        dto.setStartTime(formatTime(r.getStartTime()));
        dto.setEndTime(formatTime(r.getEndTime()));
        dto.setTotalDistance(r.getTotalDistance() != null
                ? String.valueOf(r.getTotalDistance()) : "");

        dto.setStations(stations.stream().map(s -> {
            RouteStationDTO sd = new RouteStationDTO();
            sd.setId(s.getId());
            sd.setSerial(s.getSerial());
            sd.setStation(s.getStationName());
            sd.setDistance(s.getDistanceFromPrev());
            sd.setArrival(formatTime(s.getArrivalTime()));
            sd.setDepart(formatTime(s.getDepartureTime()));
            return sd;
        }).collect(Collectors.toList()));

        return dto;
    }

    private LocalTime parseTime(String t) {
        if (t == null || t.isBlank()) return null;
        return LocalTime.parse(t);
    }

    private String formatTime(LocalTime t) {
        if (t == null) return "";
        return t.toString().substring(0, 5);
    }

    private Route require(Long id) {
        return routeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found: " + id));
    }
}