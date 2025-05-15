package com.aethercubix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import com.aethercubix.model.DetalleVenta;



public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar detalles de venta por producto, venta, etc.

    @Query("SELECT dv.producto.nombre AS nombre, SUM(dv.cantidad) AS totalVendido " +
       "FROM DetalleVenta dv GROUP BY dv.producto.nombre " +
       "ORDER BY totalVendido DESC")
List<Object[]> findTop5ProductosMasVendidos(Pageable pageable);

    
}
