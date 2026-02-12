package com.gpstracker.backend.controller;

import com.gpstracker.backend.dto.PositionHistoryDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.gpstracker.backend.service.PositionHistoryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "http://localhost:3000")
public class PositionHistoryController {

    private final PositionHistoryService positionHistoryService;

    public PositionHistoryController(PositionHistoryService positionHistoryService) {
        this.positionHistoryService = positionHistoryService;
    }

    @GetMapping
    public List<PositionHistoryDTO> getHistory(
            @RequestParam Long deviceId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to
    ) {
        return positionHistoryService.getDeviceHistory(deviceId, from, to);
    }
}

