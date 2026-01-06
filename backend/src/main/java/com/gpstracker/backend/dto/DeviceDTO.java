package com.gpstracker.backend.dto;

import lombok.Data;

@Data
public class DeviceDTO {

    private Long id;
    private String name;
    private String uniqueId;
    private String status;
    private Boolean disabled;
}
