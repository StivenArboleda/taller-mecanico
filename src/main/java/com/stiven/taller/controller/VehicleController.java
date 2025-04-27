package com.stiven.taller.controller;

import com.stiven.taller.dto.VehicleRequest;
import com.stiven.taller.dto.VehicleResponse;
import com.stiven.taller.model.vehiculo.Vehiculo;
import com.stiven.taller.service.ClienteService;
import com.stiven.taller.service.VehiculoService;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Tag(name = "Gestión de Vehículos", description = "Operaciones relacionadas con los vehículos registrados en el sistema.")
public class VehicleController {

    private final VehiculoService vehicleService;

    @Autowired
    private ClienteService clienteService;

    @Operation(
            summary = "Crear un nuevo vehículo",
            description = "Registra un nuevo vehículo asociado a un cliente mediante la cédula del cliente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VehicleResponse.class),
                            examples = @ExampleObject(value = """
                {
                  "placa": "ABC123",
                  "marca": "Toyota",
                  "modelo": "Corolla",
                  "anio": 2020,
                  "color": "Rojo",
                  "clienteCedula": "1234567890"
                }
                """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content())
    })
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<VehicleResponse> create(@RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.createVehicle(request));
    }

    @Operation(
            summary = "Obtener todos los vehículos",
            description = "Obtiene una lista de todos los vehículos registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vehículos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VehicleResponse.class)),
                            examples = @ExampleObject(value = """
                [
                  {
                    "placa": "ABC123",
                    "marca": "Toyota",
                    "modelo": "Corolla",
                    "anio": 2020,
                    "color": "Rojo",
                    "clienteCedula": "1234567890"
                  }
                ]
                """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "No se encontraron vehículos", content = @Content())
    })
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<VehicleResponse>> getAll() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }


    @Operation(
            summary = "Actualizar vehículo por placa",
            description = "Actualiza la información de un vehículo especificando su placa."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VehicleResponse.class),
                            examples = @ExampleObject(value = """
                {
                  "placa": "ABC123",
                  "marca": "Toyota",
                  "modelo": "Corolla",
                  "anio": 2020,
                  "color": "Rojo",
                  "clienteCedula": "1234567890"
                }
                """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content())
    })
    @PutMapping("/{placa}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<VehicleResponse> update(@PathVariable String placa, @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.updateVehicle(placa, request));
    }

    @Operation(
            summary = "Eliminar vehículo por ID",
            description = "Elimina un vehículo especificado por su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehículo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> delete(@PathVariable String placa) {
        vehicleService.deleteVehicle(placa);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtener vehículo por placa",
            description = "Obtiene un vehículo a partir de su placa."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo encontrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vehiculo.class),
                            examples = @ExampleObject(value = """
                {
                  "placa": "ABC123",
                  "marca": "Toyota",
                  "modelo": "Corolla",
                  "anio": 2020,
                  "color": "Rojo",
                  "clienteCedula": "1234567890"
                }
                """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content())
    })
    @GetMapping("/placa/{placa}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Vehiculo> getVehicleByPlaca(@PathVariable String placa) {
        Vehiculo vehiculo = vehicleService.getVehicleByPlaca(placa);
        return ResponseEntity.ok(vehiculo);
    }

    @Operation(
            summary = "Obtener vehículos por cédula de cliente",
            description = "Obtiene todos los vehículos asociados a un cliente mediante su cédula."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vehículos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VehicleResponse.class)),
                            examples = @ExampleObject(value = """
                [
                  {
                    "placa": "ABC123",
                    "marca": "Toyota",
                    "modelo": "Corolla",
                    "anio": 2020,
                    "color": "Rojo",
                    "clienteCedula": "1234567890"
                  }
                ]
                """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content())
    })
    @GetMapping("/cliente/{clienteCedula}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<VehicleResponse>> getVehiculosByCliente(@PathVariable String clienteCedula) {
        List<VehicleResponse> vehiculos = vehicleService.getVehiculosByCliente(clienteCedula);
        return ResponseEntity.ok(vehiculos);
    }

}
