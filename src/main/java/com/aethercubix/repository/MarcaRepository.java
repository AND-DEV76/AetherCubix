package com.aethercubix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aethercubix.model.Marca;

public interface MarcaRepository  extends JpaRepository<Marca, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar marcas por nombre, etc.
    
    Marca findByNombre(String nombre);

     boolean existsByNombre(String nombre);
}
