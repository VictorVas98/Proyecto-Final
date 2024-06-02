package com.proyecto.e_commerce.e_commerce.service;

import com.proyecto.e_commerce.e_commerce.entity.Carrito;
import com.proyecto.e_commerce.e_commerce.entity.Cliente;
import com.proyecto.e_commerce.e_commerce.entity.ProductoCarrito;
import com.proyecto.e_commerce.e_commerce.repository.CarritoRepository;
import com.proyecto.e_commerce.e_commerce.repository.ClienteRepository;
import com.proyecto.e_commerce.e_commerce.repository.CompraProductoRepository;
import com.proyecto.e_commerce.e_commerce.repository.ProductoCarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoCarritoRepository productoCarritoRepository;

    @Override
    public Carrito saveCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    @Override
    public List<Carrito> getAllCarritos() {
        return carritoRepository.findAll();
    }

    @Override
    public Carrito getCarritoById(Long id) {
        return carritoRepository.findById(id).orElse(null);
    }

    @Override
    public Carrito getCarritoByCliente_Id(Long id) {
        Carrito carrito = carritoRepository.findCarritoByCliente_Id(id);
        if (carrito != null) return carrito;
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente != null) {
            carrito = new Carrito();
            carrito.setCliente(cliente);
            return carritoRepository.save(carrito);
        }
        return null;
    }

    @Override
    public Carrito updateCarritoByCliente(Carrito carrito) {
        Cliente cliente = carrito.getCliente();
        if (cliente != null) {
            Long clienteId = cliente.getId();
            Carrito carritoByClienteId = carritoRepository.findCarritoByCliente_Id(clienteId);
            if (carritoByClienteId != null) {
                carrito.setId(carrito.getId());
            }
            productoCarritoRepository.saveAll(carrito.getProductos());
            Carrito newCarrito = carritoRepository.save(carrito);
            carrito.getProductos().forEach(pc -> {
                pc.setCarrito(newCarrito);
                productoCarritoRepository.save(pc);
            });
            return newCarrito;
        } else {
            throw new IllegalArgumentException("Cliente no puede ser null");
        }
    }

    @Override
    public Carrito updateCarrito(Long id, Carrito carrito) {
        if (carritoRepository.existsById(id)) {
            carrito.setId(id);
            return carritoRepository.save(carrito);
        } else {
            throw new RuntimeException("Carrito no encontrado");
        }
    }

    @Override
    public void deleteCarrito(Long id) {
        // Actualizar el carrito para que no tenga productos
        Carrito carrito = carritoRepository.findCarritoByCliente_Id(id);
        if (carrito != null) {
            carrito.getProductos().clear();
            carritoRepository.save(carrito);
        }
    }
}
