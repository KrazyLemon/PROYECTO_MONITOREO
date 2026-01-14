package com.odis.monitoreo.demo.plant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlantRequest {
    private String name;
    private String ubication;
    private String vpnIp;
    private String ipVnc;
}
