package com.gpstracker.backend.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.gpstracker.backend.dto.DeviceLivePatchDTO;

@Service
public class LivePatchPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public LivePatchPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void publish(DeviceLivePatchDTO dto) {
        System.out.println("Publishing LivePatchDTO " + dto);
        messagingTemplate.convertAndSend("/topic/live", dto);
    }
}
