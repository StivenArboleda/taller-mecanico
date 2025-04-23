package com.stiven.taller.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Enum que representa los posibles estados de una cita.")
public enum AppointmentStatus {

    @Schema(description = "La cita ha sido registrada pero aún no ha sido confirmada.")
    PENDIENTE,

    @Schema(description = "La cita ha sido confirmada por el taller.")
    CONFIRMADA,

    @Schema(description = "La cita fue cancelada por el cliente o el taller.")
    CANCELADA,

    @Schema(description = "La cita ha sido completada y el vehículo fue entregado.")
    COMPLETADA
}