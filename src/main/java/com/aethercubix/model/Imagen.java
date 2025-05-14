package com.aethercubix.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Imagen")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Imagen {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_imagen;

    @Column(name = "nombre_imagen", nullable = false, length = 255)
    private String nombreImagen;

 @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;


    
    
}
