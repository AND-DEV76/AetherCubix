package com.aethercubix.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aethercubix.model.Proveedor;

public interface ProveedorRepository  extends JpaRepository <Proveedor, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, buscar proveedores por nombre o email

    Optional<Proveedor> findByEmail(String email);
    
}
