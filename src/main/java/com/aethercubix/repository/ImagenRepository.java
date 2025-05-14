package com.aethercubix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aethercubix.model.Imagen;



public interface ImagenRepository extends JpaRepository<Imagen, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar imágenes por nombre o por producto
@Query("SELECT i FROM Imagen i WHERE i.producto.id_producto = :id_producto")
List<Imagen> findByProductoId(@Param("id_producto") Long id_producto);


    
    
} 