package com.proyecto.principal.service;

import com.proyecto.principal.entity.Producto;

import java.util.List;

public interface ProductoService {
    Producto saveProducto(Producto producto);
    List<Producto> getAllProductos();
    Producto getProductoById(Long id);
    void deleteProducto(Long id);
    Producto updateProducto(Long id, Producto producto);
    Producto updateStock(Long id, Integer cantidad);
}
