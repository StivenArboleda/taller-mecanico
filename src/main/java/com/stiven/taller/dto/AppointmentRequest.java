package com.stiven.taller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "Cuerpo de solicitud para la creación y actualización de una cita.")
public class AppointmentRequest {

    @Schema(description = "Fecha y hora programada de la cita", example = "2025-05-10T10:30:00")
    private LocalDateTime fechaCita;

    @Schema(description = "Motivo de la cita, por ejemplo: mantenimiento, revisión, diagnóstico", example = "Mantenimiento preventivo")
    private String motivo;

    @Schema(
            description = "Estado actual de la cita. Valores posibles: PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA",
            example = "PENDIENTE"
    )
    private String estado;

    @Schema(description = "Cedula del cliente que agenda la cita", example = "1")
    private String clienteCedula;

    @Schema(description = "Placa del vehículo vinculado a la cita", example = "ABC123")
    private String vehiculoPlaca;
}
