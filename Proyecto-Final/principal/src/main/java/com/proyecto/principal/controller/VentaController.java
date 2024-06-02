package com.proyecto.principal.controller;

import com.proyecto.principal.entity.Venta;
import com.proyecto.principal.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public List<Venta> getAllVentas() {
        return ventaService.getAllVentas();
    }

    @GetMapping("/{id}")
    public Venta getVentaById(@PathVariable Long id) {
        return ventaService.getVentaById(id);
    }

    @PostMapping
    public Venta createVenta(@RequestBody Venta venta) {
        return ventaService.saveVenta(venta);
    }

    @PutMapping("/{id}")
    public Venta updateVenta(@PathVariable Long id, @RequestBody Venta venta) {
        return ventaService.updateVenta(id, venta);
    }

    @DeleteMapping("/{id}")
    public void deleteVenta(@PathVariable Long id) {
        ventaService.deleteVenta(id);
    }

    @GetMapping("/fecha")
    public List<Venta> getVentasByFecha(@RequestParam LocalDate inicio, @RequestParam LocalDate fin) {
        return ventaService.findByFechaBetween(inicio, fin);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Venta> getVentasByCliente(@PathVariable Long clienteId) {
        return ventaService.findByClienteId(clienteId);
    }

    @GetMapping("/producto/{productoId}")
    public List<Venta> getVentasByProducto(@PathVariable Long productoId) {
        return ventaService.findByProductoId(productoId);
    }

    @GetMapping("/proveedor/{proveedorId}")
    public List<Venta> getVentasByProveedor(@PathVariable Long proveedorId) {
        return ventaService.findByProveedorId(proveedorId);
    }
}
