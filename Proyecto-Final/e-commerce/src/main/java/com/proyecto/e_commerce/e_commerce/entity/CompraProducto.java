package com.proyecto.e_commerce.e_commerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Positive(message = "La cantidad debe ser positiva")
    private Integer cantidad;

    @Override
    public String toString() {
        return "CompraProducto [id=" + id + ", producto=" + producto + ", cantidad=" + cantidad + "]";
    }
}
