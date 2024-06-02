package com.proyecto.principal.repository;

import com.proyecto.principal.entity.VentaProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaProductoRepository extends JpaRepository<VentaProducto, Long> {
}