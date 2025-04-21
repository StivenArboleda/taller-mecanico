package com.stiven.taller.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AppointmentRequest {

    private LocalDateTime fechaCita;
    private String motivo;
    private String estado;
    private Long clienteId;
    private String vehiculoPlaca;
}
