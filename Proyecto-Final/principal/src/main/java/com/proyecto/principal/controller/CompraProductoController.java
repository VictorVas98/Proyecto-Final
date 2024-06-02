package com.proyecto.principal.controller;

import com.proyecto.principal.entity.CompraProducto;
import com.proyecto.principal.service.CompraProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compra-productos")
public class CompraProductoController {

    @Autowired
    private CompraProductoService compraProductoService;

    @GetMapping
    public List<CompraProducto> getAllCompraProductos() {
        return compraProductoService.getAllCompraProductos();
    }

    @GetMapping("/{id}")
    public CompraProducto getCompraProductoById(@PathVariable Long id) {
        return compraProductoService.getCompraProductoById(id);
    }

    @PostMapping
    public CompraProducto createCompraProducto(@RequestBody CompraProducto compraProducto) {
        return compraProductoService.saveCompraProducto(compraProducto);
    }

    @PutMapping("/{id}")
    public CompraProducto updateCompraProducto(@PathVariable Long id, @RequestBody CompraProducto compraProducto) {
        return compraProductoService.updateCompraProducto(id, compraProducto);
    }

    @DeleteMapping("/{id}")
    public void deleteCompraProducto(@PathVariable Long id) {
        compraProductoService.deleteCompraProducto(id);
    }
}
