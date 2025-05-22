package com.aethercubix.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "Venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {



     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_venta;

    private LocalDate fecha_venta;

    private Double total;

    @ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "id_cliente")
private Cliente cliente;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "id_metodo")
private MetodoPago metodoPago;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "id_estado")
private EstadoVenta estadoVenta;



    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();
    
}
