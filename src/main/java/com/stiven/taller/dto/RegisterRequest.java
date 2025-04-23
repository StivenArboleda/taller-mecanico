package com.stiven.taller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Datos necesarios para registrar un nuevo usuario.")
public class RegisterRequest {
    @Schema(description = "Nombre de usuario", example = "admin")
    private String username;

    @Schema(description = "Contraseña del usuario", example = "admin")
    private String password;
}