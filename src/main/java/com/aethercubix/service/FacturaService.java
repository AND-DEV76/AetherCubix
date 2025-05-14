package com.aethercubix.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aethercubix.model.DetalleVenta;
import com.aethercubix.model.Producto;
import com.aethercubix.model.Venta;
import com.aethercubix.repository.DetalleVentaRepository;
import com.aethercubix.repository.ProductoRepository;
import com.aethercubix.repository.VentaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacturaService {
    

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleRepository;
    private final ProductoRepository productoRepository;

    @Transactional
    public Venta guardarFactura(Venta venta, List<Long> idProductos, List<Integer> cantidades) {
        List<DetalleVenta> detalles = new ArrayList<>();

        for (int i = 0; i < idProductos.size(); i++) {
            Producto producto = productoRepository.findById(idProductos.get(i))
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            int cantidadSolicitada = cantidades.get(i);

            if (producto.getStock() < cantidadSolicitada) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Restar stock
            producto.setStock(producto.getStock() - cantidadSolicitada);
            productoRepository.save(producto);

            // Crear el detalle
            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(cantidadSolicitada);
            detalle.setVenta(venta);
            detalle.setPrecio_unitario(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio() * cantidadSolicitada);
            detalles.add(detalle);
        }

        venta.setDetalles(detalles);
        Venta ventaGuardada = ventaRepository.save(venta);
        detalleRepository.saveAll(detalles);

        return ventaGuardada;
    }


    public List<Venta> obtenerTodasLasVentas() {
    return ventaRepository.findAll();
}

public void eliminarVenta(Long id) {
    ventaRepository.deleteById(id);
}



}
