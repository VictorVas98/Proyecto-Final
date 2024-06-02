package com.proyecto.e_commerce.e_commerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.e_commerce.e_commerce.config.NotificationProducer;
import com.proyecto.e_commerce.e_commerce.entity.Compra;
import com.proyecto.e_commerce.e_commerce.entity.Venta;
import com.proyecto.e_commerce.e_commerce.repository.VentaProductoRepository;
import com.proyecto.e_commerce.e_commerce.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraServiceImpl implements CompraService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VentaProductoRepository ventaProductoRepository;

    @Autowired
    private NotificationProducer notificationProducer;

    @Override
    public Venta saveCompra(Venta venta) {
        venta.setEstado(Venta.EstadoVenta.RECIBIDO);
        ventaProductoRepository.saveAll(venta.getVentaProductos());

        Venta nuevaVenta = ventaRepository.save(venta);

        venta.getVentaProductos().forEach(cp -> {
            cp.setVenta(nuevaVenta);
            ventaProductoRepository.save(cp);
        });

        notificationProducer.sendMessage("Nueva Compra: " + convertirVentaAString(nuevaVenta));
        return nuevaVenta;
    }

    @Override
    public List<Venta> getAllCompras() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta getCompraById(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public Venta updateCompra(Long id, Venta venta) {
        if (ventaRepository.existsById(id)) {
            venta.setId(id);
            return ventaRepository.save(venta);
        } else {
            throw new RuntimeException("Compra no encontrada");
        }
    }

    @Override
    public void deleteCompra(Long id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Compra no encontrada");
        }
    }

    private String convertirVentaAString(Venta venta) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(venta);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
