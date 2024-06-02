package com.proyecto.principal.service;

import com.proyecto.principal.entity.Compra;
import com.proyecto.principal.entity.Venta;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface VentaService {
    Venta saveVenta(Venta venta);
    List<Venta> getAllVentas();
    Venta getVentaById(Long id);
    void deleteVenta(Long id);
    Venta updateVenta(Long id, Venta venta);

    List<Venta> findByFechaBetween(LocalDate startDate, LocalDate endDate);
    List<Venta> findByClienteId(Long clienteId);
    List<Venta> findByProveedorId(Long proveedorId);
    List<Venta> findByProductoId(Long productoId);
}
