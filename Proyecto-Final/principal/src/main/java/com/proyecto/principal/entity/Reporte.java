package com.proyecto.principal.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.*;

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
