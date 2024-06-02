package com.proyecto.principal.service;

import com.proyecto.principal.entity.Proveedor;

import java.util.List;

public interface ProveedorService {
    Proveedor saveProveedor(Proveedor proveedor);
    List<Proveedor> getAllProveedores();
    Proveedor getProveedorById(Long id);
    void deleteProveedor(Long id);
    Proveedor updateProveedor(Long id, Proveedor proveedor);
}
