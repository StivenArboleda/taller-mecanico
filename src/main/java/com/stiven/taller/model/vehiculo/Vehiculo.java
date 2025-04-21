package com.stiven.taller.model.vehiculo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stiven.taller.model.cliente.Cliente;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehiculo {

    @Id
    private String placa;
    private String marca;
    private String modelo;
    private Integer anio;
    private String color;
    private LocalDateTime fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "cliente_cedula", referencedColumnName = "cedula")
    @JsonBackReference
    private Cliente cliente;

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
    }

}
