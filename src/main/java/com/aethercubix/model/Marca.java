package com.aethercubix.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Marca")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Marca {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_marca")
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;
    
}
