package com.proyecto.interfaz.negocio.model;

public class Producto {
    private Long id;
    private String nombre;
    private Double precio;
    private Integer stock;
    private Proveedor proveedor;
    private Categoria categoria;

    public Producto() {
    }

    public Producto(String nombre, Double precio, Integer stock, Proveedor proveedor, Categoria categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
        this.categoria = categoria;
    }

    public Producto(Long id, String nombre, Double precio, Integer stock, Proveedor proveedor, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    // Constructores, getters y setters

    public String getCategoriaNombre() {
        return categoria != null ? categoria.getNombre() : null;
    }

    public String getProveedorNombre() {
        return proveedor != null ? proveedor.getNombre() : null;
    }
}
