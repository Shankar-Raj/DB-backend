package com.gpstracker.backend.service;

import org.springframework.stereotype.Component;

@Component
public class PositionPollingWorker implements Runnable {

    private final PositionPollingService service;

    public PositionPollingWorker(PositionPollingService service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.pollInternal();
    }
}
