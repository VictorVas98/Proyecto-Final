package com.proyecto.principal.service;

import com.proyecto.principal.entity.Proveedor;
import com.proyecto.principal.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public Proveedor saveProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public List<Proveedor> getAllProveedores() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor getProveedorById(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteProveedor(Long id) {
        if (proveedorRepository.existsById(id)) {
            proveedorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Proveedor no encontrado");
        }
    }

    @Override
    public Proveedor updateProveedor(Long id, Proveedor proveedor) {
        if (proveedorRepository.existsById(id)) {
            proveedor.setId(id);
            return proveedorRepository.save(proveedor);
        } else {
            throw new RuntimeException("Proveedor no encontrado");
        }
    }
}
