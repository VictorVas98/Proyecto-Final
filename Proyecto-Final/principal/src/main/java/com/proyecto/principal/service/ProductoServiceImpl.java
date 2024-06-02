package com.proyecto.principal.service;

import com.proyecto.principal.entity.Producto;
import com.proyecto.principal.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Producto no encontrado");
        }
    }

    @Override
    public Producto updateProducto(Long id, Producto producto) {
        if (productoRepository.existsById(id)) {
            producto.setId(id);
            return productoRepository.save(producto);
        } else {
            throw new RuntimeException("Producto no encontrado");
        }
    }

    @Override
    public Producto updateStock(Long id, Integer cantidad) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            if (producto.getStock() < cantidad) {
                throw new RuntimeException("No hay suficiente stock");
            }
            producto.setStock(producto.getStock() - cantidad);
            return productoRepository.save(producto);
        } else {
            throw new RuntimeException("Producto no encontrado");
        }
    }
}
