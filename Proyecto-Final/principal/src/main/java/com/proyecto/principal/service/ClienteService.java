package com.proyecto.principal.service;
import com.proyecto.principal.entity.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente saveCliente(Cliente cliente);
    List<Cliente> getAllClientes();
    Cliente getClienteById(Long id);
    void deleteCliente(Long id);
    Cliente updateCliente(Long id, Cliente cliente);
}
