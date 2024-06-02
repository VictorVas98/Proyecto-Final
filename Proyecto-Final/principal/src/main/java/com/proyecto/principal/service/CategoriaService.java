package com.proyecto.principal.service;

import com.proyecto.principal.entity.Categoria;
import java.util.List;

public interface CategoriaService {
    Categoria saveCategoria(Categoria categoria);
    List<Categoria> getAllCategorias();
    Categoria getCategoriaById(Long id);
    void deleteCategoria(Long id);
    Categoria updateCategoria(Long id, Categoria categoria);
}
