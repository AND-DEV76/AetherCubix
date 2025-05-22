package com.aethercubix.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aethercubix.dto.DetalleVentaDTO;
import com.aethercubix.dto.VentaDTO;
import com.aethercubix.model.Venta;
import com.aethercubix.repository.VentaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaService {

    
    private final VentaRepository ventaRepository;

    public List<VentaDTO> obtenerTodasLasVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        return ventas.stream().map(this::mapToDTO).toList();
    }

 public List<VentaDTO> buscarVentas(String filtro) {
    List<Venta> ventas = ventaRepository.findByFiltro(filtro);
    return ventas.stream().map(this::mapToDTO).toList();
}



public List<VentaDTO> listarTodas() {
    List<Venta> ventas = ventaRepository.findAll();
    return ventas.stream().map(this::mapToDTO).toList();
}

    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }

    private VentaDTO mapToDTO(Venta venta) {
        VentaDTO dto = new VentaDTO();
        dto.setId_venta(venta.getId_venta());
        dto.setNombre_cliente(venta.getCliente().getNombre());
        dto.setNit_cliente(venta.getCliente().getNit());
        dto.setMetodo_pago(venta.getMetodoPago().getNombre());
        dto.setEstado(venta.getEstadoVenta().getNombre());
        dto.setTotal(venta.getTotal());

        List<DetalleVentaDTO> detalles = venta.getDetalles().stream().map(detalle -> {
            DetalleVentaDTO d = new DetalleVentaDTO();
            d.setProducto(detalle.getProducto().getNombre());
            d.setCantidad(detalle.getCantidad());
            d.setPrecio_unitario(detalle.getPrecio_unitario());
            return d;
        }).toList();

        dto.setDetalles(detalles);
        return dto;
    }
    
}
