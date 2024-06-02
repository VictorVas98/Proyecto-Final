package com.proyecto.e_commerce.e_commerce.controller;

import com.proyecto.e_commerce.e_commerce.entity.Producto;
import com.proyecto.e_commerce.e_commerce.repository.ProductoRepository;
import com.proyecto.e_commerce.e_commerce.service.ProductoService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public Page<Producto> getProductos(Pageable pageable) {
        return productoService.getProductos(pageable);
    }

    @GetMapping("/{id}")
    public Producto getProductoById(@PathVariable Long id) {
        return productoService.getProductoById(id);
    }

    @GetMapping("/categoria/{categoriaId}")
    public Page<Producto> getProductosByCategoria(@PathVariable Long categoriaId, Pageable pageable) {
        return productoService.getProductosByCategoria(categoriaId, pageable);
    }
}