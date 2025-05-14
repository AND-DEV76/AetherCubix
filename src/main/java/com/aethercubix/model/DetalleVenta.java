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
    private Long id_detalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venta")
    private Venta venta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    private Integer cantidad;

    private Double precio_unitario;

    private Double subtotal;

    @PrePersist
    @PreUpdate
    public void calcularSubtotal() {
        this.precio_unitario = producto.getPrecio();
        this.subtotal = this.precio_unitario * this.cantidad;
    }
    
}
