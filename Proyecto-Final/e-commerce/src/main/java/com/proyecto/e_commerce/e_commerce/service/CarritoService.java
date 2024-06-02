package com.proyecto.e_commerce.e_commerce.service;

import com.proyecto.e_commerce.e_commerce.entity.Carrito;

import java.util.List;

public interface CarritoService {
    Carrito saveCarrito(Carrito carrito);
    List<Carrito> getAllCarritos();
    Carrito getCarritoById(Long id);
    Carrito getCarritoByCliente_Id(Long id);
    Carrito updateCarritoByCliente(Carrito carrito);
    Carrito updateCarrito(Long id, Carrito carrito);
    void deleteCarrito(Long id);
}
