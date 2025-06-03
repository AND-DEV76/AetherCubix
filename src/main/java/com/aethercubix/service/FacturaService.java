package com.aethercubix.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aethercubix.model.Cliente;
import com.aethercubix.model.DetalleVenta;
import com.aethercubix.model.EstadoVenta;
import com.aethercubix.model.MetodoPago;
import com.aethercubix.model.Producto;
import com.aethercubix.model.Venta;
import com.aethercubix.repository.ClienteRepository;

import com.aethercubix.repository.EstadoVentaRepository;
import com.aethercubix.repository.MetodoPagoRepository;
import com.aethercubix.repository.ProductoRepository;
import com.aethercubix.repository.VentaRepository;
import com.aethercubix.repository.DetalleVentaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacturaService {


       private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final EstadoVentaRepository estadoVentaRepository;

    @Transactional
    public Venta crearVenta(Long idCliente, Long idMetodo, Long idEstado, List<Long> productoIds, List<Integer> cantidades) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow();
        MetodoPago metodo = metodoPagoRepository.findById(idMetodo).orElseThrow();
        EstadoVenta estado = estadoVentaRepository.findById(idEstado).orElseThrow();

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setMetodoPago(metodo);
        venta.setEstadoVenta(estado);
        venta.setFecha_venta(LocalDate.now());
        venta.setTotal(0.0); // Se calcula después

        List<DetalleVenta> detalles = new ArrayList<>();
        double total = 0.0;

        for (int i = 0; i < productoIds.size(); i++) {
            Producto producto = productoRepository.findById(productoIds.get(i)).orElseThrow();
            int cantidad = cantidades.get(i);
            double precio = producto.getPrecio();
            double subtotal = cantidad * precio;

            if (producto.getStock() < cantidad) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - cantidad);
            productoRepository.save(producto);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecio_unitario(precio);
            detalle.setSubtotal(subtotal);
            detalles.add(detalle);
            total += subtotal;
        }

        venta.setTotal(total);
        venta.setDetalles(detalles);
        ventaRepository.save(venta);
        detalleVentaRepository.saveAll(detalles);

        return venta;
    }
    







}
