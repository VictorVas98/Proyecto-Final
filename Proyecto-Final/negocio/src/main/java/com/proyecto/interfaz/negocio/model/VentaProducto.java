package com.proyecto.interfaz.negocio.model;

public class VentaProducto {
    private Long id;
    private Producto producto;
    private int cantidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getProductoId() {
        return producto != null ? producto.getId() : null;
    }

    public String getProductoNombre() {
        return producto != null ? producto.getNombre() : null;
    }

    public Double getPrecio() {
        return producto != null ? producto.getPrecio() : null;
    }

    public Double getSubtotal() {
        return producto != null ? producto.getPrecio() * cantidad : null;
    }
}
