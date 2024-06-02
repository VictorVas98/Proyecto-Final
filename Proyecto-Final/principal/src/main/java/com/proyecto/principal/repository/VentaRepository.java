package com.proyecto.principal.repository;

import com.proyecto.principal.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByFechaBetween(LocalDate startDate, LocalDate endDate);

    List<Venta> findByClienteId(Long clienteId);

    @Query("SELECT v FROM Venta v CROSS JOIN VentaProducto vp CROSS JOIN Producto p WHERE p.proveedor.id = :proveedorId")
    List<Venta> findByProveedorId(@Param("proveedorId") Long proveedorId);

    @Query("SELECT v FROM Venta v CROSS JOIN VentaProducto vp CROSS JOIN Producto p WHERE p.id = :productoId")
    List<Venta> findByProductoId(@Param("productoId") Long productoId);
}