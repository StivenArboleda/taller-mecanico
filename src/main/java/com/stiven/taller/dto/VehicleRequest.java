package com.stiven.taller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Data
@Setter
public class VehicleRequest {
    private String placa;
    private String marca;
    private String modelo;
    private Integer anio;
    private String color;
    private String clienteCedula;
}
