package com.proyecto.e_commerce.e_commerce.repository;
import com.proyecto.e_commerce.e_commerce.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Page<Producto> findByCategoriaId(Long categoriaId, Pageable pageable);
    Page<Producto> findAll(Pageable pageable);
}
