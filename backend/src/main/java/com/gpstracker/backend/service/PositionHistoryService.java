package com.gpstracker.backend.service;

import com.gpstracker.backend.dto.PositionHistoryDTO;
import com.gpstracker.backend.repository.HistoryPositionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PositionHistoryService {

    private final HistoryPositionRepository historyPositionRepository;

    public PositionHistoryService(HistoryPositionRepository historyPositionRepository) {
        this.historyPositionRepository = historyPositionRepository;
    }

    public List<PositionHistoryDTO> getDeviceHistory(
            Long deviceId,
            LocalDateTime from,
            LocalDateTime to
    ) {
        return historyPositionRepository.findHistory(deviceId, from, to);
    }
}
