package com.gpstracker.backend.service;

import com.gpstracker.backend.dto.DeviceLivePatchDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class LivePatchPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public LivePatchPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void publish(DeviceLivePatchDTO patch) {
        messagingTemplate.convertAndSend("/topic/live", patch);
    }
}
