package com.proyecto.interfaz.negocio.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class Venta {
    private Long id;
    private Cliente cliente;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date fecha;
    private EstadoVenta estado;
    private List<VentaProducto> ventaProductos;
    public enum EstadoVenta {
        RECIBIDO, DESPACHADO, ENTREGADO
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    public List<VentaProducto> getVentaProductos() {
        return ventaProductos;
    }

    public void setVentaProductos(List<VentaProducto> ventaProductos) {
        this.ventaProductos = ventaProductos;
    }

    public String getProductos() {
        StringBuilder productos = new StringBuilder();
        for (VentaProducto ventaProducto : ventaProductos) {
            productos.append(ventaProducto.getProducto().getNombre()).append(", ");
        }
        return productos.toString();
    }

    public Double getTotal() {
        double total = 0;
        for (VentaProducto ventaProducto : ventaProductos) {
            total += ventaProducto.getProducto().getPrecio() * ventaProducto.getCantidad();
        }
        return total;
    }

    public String getClienteNombre() {
        return cliente.getNombre();
    }
}
