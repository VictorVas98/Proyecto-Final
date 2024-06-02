package com.proyecto.e_commerce.e_commerce.service;

import com.proyecto.e_commerce.e_commerce.entity.Compra;
import com.proyecto.e_commerce.e_commerce.entity.Venta;

import java.util.List;

public interface CompraService {
    Venta saveCompra(Venta compra);
    List<Venta> getAllCompras();
    Venta getCompraById(Long id);
    Venta updateCompra(Long id, Venta compra);
    void deleteCompra(Long id);
}
