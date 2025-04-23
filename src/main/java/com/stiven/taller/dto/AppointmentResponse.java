package com.stiven.taller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "Representación de los datos devueltos al consultar una cita.")
public class AppointmentResponse {

    @Schema(description = "ID único de la cita", example = "12")
    private Long id;

    @Schema(description = "Fecha y hora de la cita programada", example = "2025-05-10T10:30:00")
    private LocalDateTime fechaCita;

    @Schema(description = "Motivo registrado de la cita", example = "Cambio de aceite")
    private String motivo;

    @Schema(description = "Estado actual de la cita", example = "CONFIRMADA")
    private String estado;

    @Schema(description = "Fecha y hora en que el vehículo fue recogido, si aplica", example = "2025-05-10T16:00:00")
    private LocalDateTime fechaRecogida;

    @Schema(description = "Nombre completo del cliente", example = "Juan Pérez")
    private String clienteNombre;

    @Schema(description = "Placa del vehículo asociado a la cita", example = "ABC123")
    private String vehiculoPlaca;

}
