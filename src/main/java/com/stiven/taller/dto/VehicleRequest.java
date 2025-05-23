package com.stiven.taller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Data
@Setter
@Schema(description = "Cuerpo de solicitud para la creación y actualización de un vehículo.")
public class VehicleRequest {

    @Schema(description = "Placa del vehículo", required = true, example = "ABC123")
    private String placa;

    @Schema(description = "Marca del vehículo", required = true, example = "Toyota")
    private String marca;

    @Schema(description = "Modelo del vehículo", required = true, example = "Corolla")
    private String modelo;

    @Schema(description = "Año de fabricación del vehículo", required = true, example = "2021")
    private Integer anio;

    @Schema(description = "Color del vehículo", required = true, example = "Rojo")
    private String color;

    @Schema(description = "Cédula del cliente propietario del vehículo", required = true, example = "1234567890")
    private String clienteCedula;
}
