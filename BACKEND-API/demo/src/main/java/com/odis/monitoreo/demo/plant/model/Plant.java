package com.odis.monitoreo.demo.plant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plantas")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "ubicacion")
    private String ubication;
    @Column(name = "vpn_ip")
    private String vpnIp;
    @Column(name = "ip_vnc")
    private String ipVnc;

}
