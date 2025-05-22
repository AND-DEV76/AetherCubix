package com.aethercubix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aethercubix.model.MetodoPago;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar métodos de pago por nombre, etc.


    Optional<MetodoPago> findByNombre(String nombre);

    
}
