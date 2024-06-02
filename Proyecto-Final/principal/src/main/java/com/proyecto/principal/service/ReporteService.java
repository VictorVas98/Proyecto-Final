package com.proyecto.principal.service;

import com.proyecto.principal.entity.Reporte;

import java.time.LocalDate;
import java.util.Date;

public interface ReporteService {
    Reporte generarReportePorTiempo(LocalDate inicio, LocalDate fin);
    Reporte generarReportePorProducto(Long id);
    Reporte generarReportePorCliente(Long id);
    Reporte generarReportePorProveedor(Long id);
}
