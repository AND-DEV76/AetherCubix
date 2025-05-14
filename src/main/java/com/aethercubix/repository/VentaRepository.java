package com.aethercubix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aethercubix.model.Venta;




public interface VentaRepository  extends JpaRepository<Venta, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar ventas por cliente, fecha, etc.

    
} 