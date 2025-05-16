package com.aethercubix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aethercubix.model.Venta;




public interface VentaRepository  extends JpaRepository<Venta, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar ventas por cliente, fecha, etc.


    @Query("SELECT DISTINCT v FROM Venta v JOIN v.detalles d JOIN d.producto p " +
       "WHERE LOWER(v.nombre_cliente) LIKE %:filtro% " +
       "OR LOWER(v.nit_cliente) LIKE %:filtro% " +
       "OR CAST(v.total AS string) LIKE %:filtro% " +
       "OR LOWER(v.metodo_pago) LIKE %:filtro% " +
       "OR LOWER(v.estado) LIKE %:filtro% " +
       "OR LOWER(p.nombre) LIKE %:filtro%")
List<Venta> buscarPorCampos(@Param("filtro") String filtro);


    
} 