package com.aethercubix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aethercubix.model.EstadoVenta;
import com.aethercubix.model.MetodoPago;
import com.aethercubix.model.Venta;



public interface VentaRepository extends JpaRepository<Venta, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar ventas por fecha, cliente, etc
    /*
     * 
     * 
     * 
     * @Query("SELECT v FROM Venta v WHERE LOWER(v.cliente.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
           "OR LOWER(v.cliente.nit) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Venta> findByClienteNombreOrNitContainingIgnoreCase(@Param("filtro") String filtro);

     * 
     * 
     */
 @Query("SELECT v FROM Venta v " +
       "WHERE LOWER(v.cliente.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
       "OR LOWER(v.cliente.nit) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
       "OR LOWER(v.metodoPago.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
       "OR LOWER(v.estadoVenta.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
       "OR CAST(v.total AS string) LIKE CONCAT('%', :filtro, '%')")
List<Venta> findByFiltro(@Param("filtro") String filtro);


//mantenemos la realcion con el cliente y la venta
    @Query("SELECT COUNT(v) > 0 FROM Venta v WHERE v.cliente.id_cliente = :idCliente")
    boolean existsVentaByClienteId(@Param("idCliente") Long idCliente);



     boolean existsByMetodoPago(MetodoPago metodoPago);


     boolean existsByEstadoVenta(EstadoVenta estadoVenta);
    
}
