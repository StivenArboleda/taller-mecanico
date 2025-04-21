package com.stiven.taller.model.cliente;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stiven.taller.model.vehiculo.Vehiculo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Table(name = "clientes")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "cedula", nullable = false, unique = true)
    private String cedula;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private Date fechaRegistro;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Vehiculo> vehiculos = new ArrayList<>();


    @PrePersist
    public void prePersist() {
        if (fechaRegistro == null) {
            fechaRegistro = new Date();
        }
    }

}
