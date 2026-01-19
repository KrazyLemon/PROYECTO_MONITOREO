package com.odis.monitoreo.demo.plant.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.odis.monitoreo.demo.company.models.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plantas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // NOMBRE DE ACCESO A LA API
    @Column(name = "nombre")
    private String name;

    @Column(name = "token")
    private String token;

    @Column(name = "ubicacion")
    private String ubication;

    @Column(name = "vpn_ip")
    private String vpnIp;

    @Column(name = "ip_vnc")
    private String ipVnc;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Conection> conections = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Company company;

}
