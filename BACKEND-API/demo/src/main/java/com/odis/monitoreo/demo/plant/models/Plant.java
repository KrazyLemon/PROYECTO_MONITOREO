package com.odis.monitoreo.demo.plant.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.odis.monitoreo.demo.company.models.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "API_KEY")
    private String key;

    @Column(name = "ubicacion")
    private String ubication;

    @Column(name = "vpn_ip")
    private String vpnIp;

    @Column(name = "ip_vnc")
    private String ipVnc;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL)
    private Set<Conection> conections = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Company company;

}
