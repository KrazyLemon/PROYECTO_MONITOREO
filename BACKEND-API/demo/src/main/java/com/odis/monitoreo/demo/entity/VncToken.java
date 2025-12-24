package com.odis.monitoreo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name ="vnc_tokens")
@Data
public class VncToken {
    @Id
    private String token;

    @Column(nullable =false)
    private String targetIp;

    @Column(nullable = false)
    private Integer targetPort = 5900;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    private String clientId;
    private boolean active = true;

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
