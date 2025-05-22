package com.aethercubix.model;







import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "Estado_Venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoVenta {

    @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_estado;

    @Column(nullable = false, unique = true)
    private String nombre;
}
