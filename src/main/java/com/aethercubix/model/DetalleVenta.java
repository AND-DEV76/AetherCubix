package com.aethercubix.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Detalle_Venta")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DetalleVenta {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long id_detalle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "precio_unitario")
    private Double precio_unitario;

    @Column(name = "subtotal")
    private Double subtotal;
    
}
