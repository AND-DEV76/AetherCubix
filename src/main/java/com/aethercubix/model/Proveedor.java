package com.aethercubix.model;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Proveedor")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_proveedor;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 50)
    private String contacto;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String direccion;

    @Column(nullable = false)
    private LocalDate fecha_registro;


    
}
