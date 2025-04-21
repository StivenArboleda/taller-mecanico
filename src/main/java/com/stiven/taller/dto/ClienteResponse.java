package com.stiven.taller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Schema(description = "Representación de un cliente con sus datos personales y vehículos asociados")
public class ClienteResponse {

    @Schema(description = "Número de cédula del cliente", example = "1234567890")
    private String cedula;

    @Schema(description = "Nombre del cliente", example = "Juan")
    private String nombre;

    @Schema(description = "Apellido del cliente", example = "Pérez")
    private String apellido;

    @Schema(description = "Correo electrónico del cliente", example = "juan.perez@email.com")
    private String email;

    @Schema(description = "Número de teléfono del cliente", example = "0999999999")
    private String telefono;

    @Schema(description = "Dirección del cliente", example = "Av. Siempre Viva 123")
    private String direccion;

    @Schema(description = "Fecha de registro del cliente", example = "2024-04-20")
    private Date fechaRegistro;

    @Schema(description = "Lista de placas de los vehículos registrados por el cliente",
            example = "[\"ABC123\", \"XYZ789\"]")
    private List<String> vehiculos;
}