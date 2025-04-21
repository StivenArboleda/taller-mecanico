package com.stiven.taller.model.appointment;

import com.stiven.taller.enums.AppointmentStatus;
import com.stiven.taller.model.cliente.Cliente;
import com.stiven.taller.model.vehiculo.Vehiculo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaCita;

    private String motivo;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus estado;

    private LocalDateTime fechaRecogida;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;
}
