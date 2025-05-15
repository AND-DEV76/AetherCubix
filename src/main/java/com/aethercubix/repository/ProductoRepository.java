package com.aethercubix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aethercubix.model.Producto;
import com.aethercubix.model.Proveedor;



public interface ProductoRepository extends JpaRepository<Producto, Long> {

    long countByProveedor(Proveedor proveedor);
List<Producto> findTop5ByOrderByPrecioDesc();
    List<Producto> findTop5ByOrderByStockDesc();
     

    
} 