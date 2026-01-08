package com.odis.monitoreo.demo.company.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "empresa")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Audited
@Builder
public class Company {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="nombre", nullable = false)
    private String name;

    @Column(name ="super_empresa",nullable = false)
    private boolean superEmpresa = false;

}
