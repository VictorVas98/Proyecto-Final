package com.proyecto.principal.service;
import com.proyecto.principal.entity.Producto;
import com.proyecto.principal.entity.Reporte;
import com.proyecto.principal.entity.Venta;
import com.proyecto.principal.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ProductoService productoService;

    @Override
    public Reporte generarReportePorTiempo(LocalDate inicio, LocalDate fin) {
        Reporte reporte = new Reporte();
        List<Venta> ventas = ventaService.findByFechaBetween(inicio, fin);
        System.out.println(ventaService.findByFechaBetween(inicio, fin));
        reporte.setVentas(ventas);
        reporte.setTotalVentas(calcularTotalVentas(ventas));
        reporte.setFechaGeneracion(new Date());
        return reporteRepository.save(reporte);
    }

    @Override
    public Reporte generarReportePorProducto(Long id) {
        Reporte reporte = new Reporte();
        List<Venta> ventas = ventaService.findByProductoId(id);
        reporte.setVentas(ventas);
        reporte.setTotalVentas(calcularTotalVentas(ventas));
        reporte.setFechaGeneracion(new Date());
        return reporteRepository.save(reporte);
    }

    @Override
    public Reporte generarReportePorCliente(Long id) {
        Reporte reporte = new Reporte();
        List<Venta> ventas = ventaService.findByClienteId(id);
        reporte.setVentas(ventas);
        reporte.setTotalVentas(calcularTotalVentas(ventas));
        reporte.setFechaGeneracion(new Date());
        return reporteRepository.save(reporte);
    }

    @Override
    public Reporte generarReportePorProveedor(Long id) {
        Reporte reporte = new Reporte();
        List<Venta> ventas = ventaService.findByProveedorId(id);
        reporte.setVentas(ventas);
        reporte.setTotalVentas(calcularTotalVentas(ventas));
        reporte.setFechaGeneracion(new Date());
        return reporteRepository.save(reporte);
    }

    private Double calcularTotalVentas(List<Venta> ventas) {
        return ventas.stream()
                .mapToDouble(venta -> venta.getVentaProductos().stream()
                        .mapToDouble(vp -> {
                            Producto producto = productoService.getProductoById(vp.getProducto().getId());
                            return producto.getPrecio() * vp.getCantidad();
                        }).sum())
                .sum();
    }
}