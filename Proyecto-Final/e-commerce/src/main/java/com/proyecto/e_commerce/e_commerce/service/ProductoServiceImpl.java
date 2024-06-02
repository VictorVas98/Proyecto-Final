package com.proyecto.e_commerce.e_commerce.service;

import com.proyecto.e_commerce.e_commerce.entity.Producto;
import com.proyecto.e_commerce.e_commerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto getProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Producto> getProductosByCategoria(Long categoriaId, Pageable pageable) {
        return productoRepository.findByCategoriaId(categoriaId, pageable);
    }

    @Override
    public Page<Producto> getProductos(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }
}