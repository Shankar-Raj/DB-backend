package com.gpstracker.backend.service;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

@Component
public class DynamicPollingManager {

    private final TaskScheduler scheduler;
    private final PositionPollingWorker worker;

    private ScheduledFuture<?> pollingTask;

    public DynamicPollingManager(PositionPollingWorker worker) {

        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setThreadNamePrefix("LivePolling-");
        taskScheduler.initialize();

        this.scheduler = taskScheduler;
        this.worker = worker;
    }

    public synchronized void start() {

        if (pollingTask != null && !pollingTask.isCancelled()) {
            return; // already running
        }

        pollingTask = scheduler.scheduleWithFixedDelay(worker, 10000);

        System.out.println("Live polling STARTED");
    }

    public synchronized void stop() {

        if (pollingTask != null) {
            pollingTask.cancel(true);
            pollingTask = null;
            System.out.println("Live polling STOPPED");
        }
    }

    public boolean isRunning() {
        return pollingTask != null && !pollingTask.isCancelled();
    }
}
