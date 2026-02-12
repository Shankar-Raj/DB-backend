package com.gpstracker.backend.controller;

import com.gpstracker.backend.service.PositionPollingService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LiveControlController {

    private final PositionPollingService pollingService;

    public LiveControlController(PositionPollingService pollingService) {
        this.pollingService = pollingService;
    }

    @GetMapping("/live/start")
    public String start() {
        pollingService.start();
        return "Live tracking started";
    }

    @GetMapping("/live/stop")
    public String stop() {
        pollingService.stop();
        return "Live tracking stopped";
    }
}
