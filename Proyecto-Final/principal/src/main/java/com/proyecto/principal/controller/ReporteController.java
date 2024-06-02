package com.proyecto.principal.controller;

import com.proyecto.principal.entity.Reporte;
import com.proyecto.principal.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/tiempo")
    public Reporte generarReportePorTiempo(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return reporteService.generarReportePorTiempo(inicio, fin);
    }

    @GetMapping("/producto/{id}")
    public Reporte generarReportePorProducto(@PathVariable Long id) {
        return reporteService.generarReportePorProducto(id);
    }

    @GetMapping("/cliente/{id}")
    public Reporte generarReportePorCliente(@PathVariable Long id) {
        return reporteService.generarReportePorCliente(id);
    }

    @GetMapping("/proveedor/{id}")
    public Reporte generarReportePorProveedor(@PathVariable Long id) {
        return reporteService.generarReportePorProveedor(id);
    }
}
