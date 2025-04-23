package com.stiven.taller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Cuerpo de solicitud para la creación y actualización de un cliente.")
public class ClienteRequest {

    @Schema(example = "1234567890", description = "Cédula única del cliente", required = true)
    private String cedula;

    @Schema(example = "Juan", description = "Nombre del cliente", required = true)
    private String nombre;

    @Schema(example = "Pérez", description = "Apellido del cliente", required = true)
    private String apellido;

    @Schema(example = "juan.perez@email.com", description = "Correo electrónico del cliente", required = true)
    private String email;

    @Schema(example = "0999999999", description = "Número de teléfono del cliente")
    private String telefono;

    @Schema(example = "Av. Siempre Viva 123", description = "Dirección física del cliente")
    private String direccion;

}