package com.aethercubix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aethercubix.model.Producto;
import com.aethercubix.model.Proveedor;



public interface ProductoRepository extends JpaRepository<Producto, Long> {

    long countByProveedor(Proveedor proveedor);
List<Producto> findTop5ByOrderByPrecioDesc();
    List<Producto> findTop5ByOrderByStockDesc();
     
@Query("SELECT p FROM Producto p JOIN p.proveedor prov WHERE " +
       "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
       "LOWER(p.marca) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
       "CAST(p.precio AS string) LIKE CONCAT('%', :busqueda, '%') OR " +
       "CAST(p.stock AS string) LIKE CONCAT('%', :busqueda, '%') OR " +
       "p.descripcion LIKE CONCAT('%', :busqueda, '%') OR " + // sin LOWER()
       "prov.nombre LIKE CONCAT('%', :busqueda, '%')")         // sin LOWER()
List<Producto> buscarProductos(@Param("busqueda") String busqueda);


    
} 