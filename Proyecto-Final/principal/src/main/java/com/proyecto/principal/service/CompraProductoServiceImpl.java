package com.proyecto.principal.service;

import com.proyecto.principal.entity.CompraProducto;
import com.proyecto.principal.entity.Producto;
import com.proyecto.principal.repository.CompraProductoRepository;
import com.proyecto.principal.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraProductoServiceImpl implements CompraProductoService {

    @Autowired
    private CompraProductoRepository compraProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public CompraProducto saveCompraProducto(CompraProducto compraProducto) {
        Producto producto = compraProducto.getProducto();
        producto.setStock(producto.getStock() + compraProducto.getCantidad());
        productoRepository.save(producto);
        return compraProductoRepository.save(compraProducto);
    }

    @Override
    public List<CompraProducto> getAllCompraProductos() {
        return compraProductoRepository.findAll();
    }

    @Override
    public CompraProducto getCompraProductoById(Long id) {
        return compraProductoRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCompraProducto(Long id) {
        CompraProducto compraProducto = compraProductoRepository.findById(id).orElse(null);
        if (compraProducto != null) {
            Producto producto = compraProducto.getProducto();
            producto.setStock(producto.getStock() - compraProducto.getCantidad());
            productoRepository.save(producto);
            compraProductoRepository.deleteById(id);
        } else {
            throw new RuntimeException("CompraProducto no encontrado");
        }
    }

    @Override
    public CompraProducto updateCompraProducto(Long id, CompraProducto compraProducto) {
        if (compraProductoRepository.existsById(id)) {
            CompraProducto existingCompraProducto = compraProductoRepository.findById(id).orElse(null);
            if (existingCompraProducto != null) {
                Producto producto = existingCompraProducto.getProducto();
                producto.setStock(producto.getStock() - existingCompraProducto.getCantidad() + compraProducto.getCantidad());
                productoRepository.save(producto);
            }
            compraProducto.setId(id);
            return compraProductoRepository.save(compraProducto);
        } else {
            throw new RuntimeException("CompraProducto no encontrado");
        }
    }
}