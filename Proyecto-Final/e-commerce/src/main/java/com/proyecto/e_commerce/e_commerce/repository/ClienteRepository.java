package com.proyecto.e_commerce.e_commerce.repository;

import com.proyecto.e_commerce.e_commerce.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
