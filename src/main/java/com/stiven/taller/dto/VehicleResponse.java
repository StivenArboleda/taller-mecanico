package com.stiven.taller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class VehicleResponse {
    private String placa;
    private String marca;
    private String modelo;
    private Integer anio;
    private String color;
    private String clienteCedula;
}

