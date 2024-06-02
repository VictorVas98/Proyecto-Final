package com.proyecto.e_commerce.e_commerce.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.e_commerce.e_commerce.config.NotificationProducer;
import com.proyecto.e_commerce.e_commerce.entity.Compra;
import com.proyecto.e_commerce.e_commerce.entity.CompraProducto;
import com.proyecto.e_commerce.e_commerce.entity.ProductoCarrito;
import com.proyecto.e_commerce.e_commerce.entity.Venta;
import com.proyecto.e_commerce.e_commerce.repository.CompraProductoRepository;
import com.proyecto.e_commerce.e_commerce.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Autowired
    private NotificationProducer notificationProducer;

    @GetMapping
    public List<Venta> getAllCompras() {
        return compraService.getAllCompras();
    }

    @GetMapping("/{id}")
    public Venta getCompraById(@PathVariable Long id) {
        return compraService.getCompraById(id);
    }

    @PostMapping
    public Venta createCompra(@RequestBody Venta compra) {
        Venta nuevaCompra = compraService.saveCompra(compra);
        try {
            String message = convertToJson(nuevaCompra);
            notificationProducer.sendMessage("Nuevo Pedido: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nuevaCompra;
    }

    @PutMapping("/{id}")
    public Venta updateCompra(@PathVariable Long id, @RequestBody Venta compra) {
        Venta compraActualizada = compraService.updateCompra(id, compra);
        try {
            String message = convertToJson(compraActualizada);
            notificationProducer.sendMessage("Pedido Actualizado: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compraActualizada;
    }

    @DeleteMapping("/{id}")
    public void deleteCompra(@PathVariable Long id) {
        compraService.deleteCompra(id);
        notificationProducer.sendMessage("Pedido eliminado: " + id);
    }

    private String convertToJson(Venta compra) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(compra);
    }
}