package com.aethercubix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aethercubix.model.EstadoVenta;



public interface EstadoVentaRepository extends JpaRepository<EstadoVenta, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar estados de venta por nombre, etc.
    

    Optional<EstadoVenta> findByNombreIgnoreCase(String nombre);


    
}
