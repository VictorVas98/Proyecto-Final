package com.proyecto.principal.service;

import com.proyecto.principal.entity.Compra;
import com.proyecto.principal.entity.Venta;
import com.proyecto.principal.entity.VentaProducto;
import com.proyecto.principal.repository.VentaProductoRepository;
import com.proyecto.principal.repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VentaProductoRepository ventaProductoRepository;

    @Autowired
    ProductoService productoService;

    @Autowired
    private EmailService emailService;

    @Override
    public Venta saveVenta(Venta venta) {
        for (VentaProducto vp : venta.getVentaProductos()) {
            if (productoService.getProductoById(vp.getProducto().getId()) == null) {
                throw new RuntimeException("Producto no encontrado");
            }
            if (productoService.getProductoById(vp.getProducto().getId()).getStock() < vp.getCantidad()) {
                throw new RuntimeException("No hay suficiente stock");
            }
        }
        for (VentaProducto ventaProducto : venta.getVentaProductos()) {
            var producto = productoService.getProductoById(ventaProducto.getProducto().getId());
            producto.setStock(producto.getStock() - ventaProducto.getCantidad());
            productoService.saveProducto(producto);
        }
        ventaProductoRepository.saveAll(venta.getVentaProductos());

        Venta newVenta = ventaRepository.save(venta);

        venta.getVentaProductos().forEach(vp -> {
            vp.setVenta(newVenta);
            ventaProductoRepository.save(vp);
        });

        emailService.sendEmailSaleWithProducts(venta.getCliente().getEmail(), "Compra realizada", venta.getVentaProductos());

        return newVenta;
    }

    @Override
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta getVentaById(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteVenta(Long id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Venta no encontrada");
        }
    }

    @Override
    public Venta updateVenta(Long id, Venta venta) {
        if (ventaRepository.existsById(id)) {
            venta.setId(id);
            return ventaRepository.save(venta);
        } else {
            throw new RuntimeException("Venta no encontrada");
        }
    }

    @Override
    public List<Venta> findByFechaBetween(LocalDate startDate, LocalDate endDate) {
        return ventaRepository.findByFechaBetween(startDate, endDate);
    }

    @Override
    public List<Venta> findByClienteId(Long clienteId) {
        return ventaRepository.findByClienteId(clienteId);
    }

    @Override
    public List<Venta> findByProveedorId(Long proveedorId) {
        return ventaRepository.findByProveedorId(proveedorId);
    }

    @Override
    public List<Venta> findByProductoId(Long productoId) {
        return ventaRepository.findByProductoId(productoId);
    }
}
