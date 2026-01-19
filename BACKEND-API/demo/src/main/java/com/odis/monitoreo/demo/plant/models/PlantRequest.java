package com.odis.monitoreo.demo.plant.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PlantRequest {
    private String name;
    private String ubication;
    private String vpnIp;
    private String ipVnc;
    private String company;

}
