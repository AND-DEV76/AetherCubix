package com.aethercubix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aethercubix.model.DetalleVenta;



public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar detalles de venta por producto, venta, etc.
    
}
