package com.proyecto.e_commerce.e_commerce.controller;

import com.proyecto.e_commerce.e_commerce.entity.Carrito;
import com.proyecto.e_commerce.e_commerce.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public List<Carrito> getAllCarritos() {
        return carritoService.getAllCarritos();
    }

    @GetMapping("/{id}")
    public Carrito getCarritoById(@PathVariable Long id) {
        return carritoService.getCarritoByCliente_Id(id);
    }

    @PostMapping
    public Carrito addToCarrito(@RequestBody Carrito carrito) {
        return carritoService.saveCarrito(carrito);
    }

    @PutMapping
    public Carrito updateCarrito(@RequestBody Carrito carrito) {
        return carritoService.updateCarritoByCliente(carrito);
    }

    @DeleteMapping("/{id}")
    public void deleteCarrito(@PathVariable Long id) {
        carritoService.deleteCarrito(id);
    }
}
