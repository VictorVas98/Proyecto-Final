package com.proyecto.e_commerce.e_commerce.repository;

import com.proyecto.e_commerce.e_commerce.entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Carrito findCarritoByCliente_Id(Long id);
}
