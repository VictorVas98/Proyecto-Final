package com.proyecto.principal.service;

import com.proyecto.principal.entity.CompraProducto;

import java.util.List;

public interface CompraProductoService {
    CompraProducto saveCompraProducto(CompraProducto compraProducto);
    List<CompraProducto> getAllCompraProductos();
    CompraProducto getCompraProductoById(Long id);
    void deleteCompraProducto(Long id);
    CompraProducto updateCompraProducto(Long id, CompraProducto compraProducto);
}
