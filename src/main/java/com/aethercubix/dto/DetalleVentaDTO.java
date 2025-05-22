package com.aethercubix.dto;

import lombok.Data;

@Data
public class DetalleVentaDTO {

    private String producto;
    private int cantidad;
    private double precio_unitario;
    
}
