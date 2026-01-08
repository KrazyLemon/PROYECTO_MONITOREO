package com.odis.monitoreo.demo.plant.model;

import com.odis.monitoreo.demo.company.models.Company;
import com.odis.monitoreo.demo.user.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

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
