package com.proyecto.principal.entity;

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
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha de compra es obligatoria")
    private Date fechaCompra;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El estado es obligatorio")
    private EstadoCompra estado;

    @OneToMany
    private List<CompraProducto> productos;

    public enum EstadoCompra {
        RECIBIDO, PROCESANDO, DESPACHADO, ENTREGADO
    }
}
