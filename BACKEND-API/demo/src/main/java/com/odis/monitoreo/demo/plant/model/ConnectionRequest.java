package com.odis.monitoreo.demo.plant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConnectionRequest {
    private Integer plantId;
    private AccessLevel level;
    private LocalDateTime expirationTime;
}
