package com.proyecto.e_commerce.e_commerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "reporte_venta",
            joinColumns = @JoinColumn(name = "reporte_id"),
            inverseJoinColumns = @JoinColumn(name = "venta_id")
    )
    private List<Venta> ventas;

    @Column(name = "total_ventas")
    private Double totalVentas;

    @Column(name = "fecha_generacion")
    @Temporal(TemporalType.DATE)
    private Date fechaGeneracion;
}
