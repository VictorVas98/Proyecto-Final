package com.proyecto.e_commerce.e_commerce.service;

import com.proyecto.e_commerce.e_commerce.entity.Producto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductoService {
    Producto getProductoById(Long id);
    Page<Producto> getProductosByCategoria(Long categoriaId, Pageable pageable);
    Page<Producto> getProductos(Pageable pageable);
}