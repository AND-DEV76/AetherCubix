package com.aethercubix.dto;

import java.util.List;

import lombok.Data;

@Data
public class VentaDTO {

    private Long id_venta;
    private String nombre_cliente;
    private String nit_cliente;
    private String metodo_pago;
    private String estado;
    private double total;
    private List<DetalleVentaDTO> detalles;
    
}
