package com.proyecto.principal.repository;

import com.proyecto.principal.entity.CompraProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraProductoRepository extends JpaRepository<CompraProducto, Long> {
}