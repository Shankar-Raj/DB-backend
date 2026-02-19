package com.gpstracker.backend.service;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class WebSocketSubscriptionManager {

    private final AtomicInteger subscriberCount = new AtomicInteger(0);
    private final DynamicPollingManager pollingManager;

    public WebSocketSubscriptionManager(DynamicPollingManager pollingManager) {
        this.pollingManager = pollingManager;
    }

    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(event.getMessage());

        String destination = accessor.getDestination();

        if (destination != null && destination.equals("/topic/live")) {

            int count = subscriberCount.incrementAndGet();

            System.out.println("Subscriber joined. Count = " + count);

            if (count == 1) {
                pollingManager.start();
            }
        }
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {

        int count = subscriberCount.decrementAndGet();

        if (count <= 0) {
            subscriberCount.set(0);
            pollingManager.stop();
        }

        System.out.println("Subscriber left. Count = " + subscriberCount.get());
    }
}

