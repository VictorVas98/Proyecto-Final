package com.proyecto.e_commerce.e_commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha es obligatoria")
    private Date fecha;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El estado es obligatorio")
    private EstadoVenta estado;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VentaProducto> ventaProductos;

    @ManyToMany(mappedBy = "ventas")
    @JsonIgnore
    private List<Reporte> reportes;

    public enum EstadoVenta {
        RECIBIDO, DESPACHADO, ENTREGADO
    }

    @Override
    public String toString() {
        return "Venta [id=" + id + ", cliente=" + cliente + ", fecha=" + fecha + ", estado=" + estado + "]";
    }
}

