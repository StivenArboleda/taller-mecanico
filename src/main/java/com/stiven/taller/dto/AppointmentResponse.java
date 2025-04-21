package com.stiven.taller.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AppointmentResponse {

    private Long id;
    private LocalDateTime fechaCita;
    private String motivo;
    private String estado;
    private LocalDateTime fechaRecogida;
    private String clienteNombre;
    private String vehiculoPlaca;

}
