package com.stiven.taller.controller;

import com.stiven.taller.dto.AppointmentRequest;
import com.stiven.taller.dto.AppointmentResponse;
import com.stiven.taller.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Tag(name = "Gestión de Citas", description = "Operaciones relacionadas con los citas registrados en el sistema.")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(
            summary = "Registrar una nueva cita",
            description = "Crea una nueva cita vinculada a un cliente y un vehículo. El estado inicial debe ser 'PENDIENTE'.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AppointmentRequest.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de Cita",
                                    value = """
                            {
                              "fechaCita": "2025-05-10T10:30:00",
                              "motivo": "Mantenimiento preventivo",
                              "estado": "PENDIENTE",
                              "clienteId": 1,
                              "vehiculoPlaca": "ABC123"
                            }
                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cita registrada exitosamente", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content())
            }
    )
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<AppointmentResponse> create(@RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.createAppointment(request));
    }

    @Operation(
            summary = "Obtener todas las citas",
            description = "Devuelve una lista completa de todas las citas registradas en el sistema."
    )
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<AppointmentResponse>> getAll() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @Operation(
            summary = "Buscar cita por ID",
            description = "Devuelve los detalles de una cita específica según su ID.",
            parameters = @Parameter(name = "id", description = "ID de la cita", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cita encontrada", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Cita no encontrada", content = @Content())
            }
    )
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @Operation(
            summary = "Actualizar estado de la cita",
            description = "Permite modificar el estado actual de una cita (PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA).",
            parameters = @Parameter(name = "id", description = "ID de la cita", required = true),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Solo se debe proporcionar el campo `estado` en el cuerpo de la solicitud.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Actualizar estado",
                                    value = """
                            {
                              "estado": "CONFIRMADA"
                            }
                            """
                            )
                    )
            )
    )
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<AppointmentResponse> update(
            @PathVariable Long id,
            @RequestBody AppointmentRequest request
    ) {
        return ResponseEntity.ok(appointmentService.updateAppointmentStatus(id, request.getEstado()));
    }

    @Operation(
            summary = "Eliminar una cita",
            description = "Elimina una cita del sistema usando su ID.",
            parameters = @Parameter(name = "id", description = "ID de la cita a eliminar", required = true),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cita eliminada exitosamente", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Cita no encontrada", content = @Content())
            }
    )
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Marcar una cita como completada",
            description = "Cambia el estado de una cita a 'COMPLETADA' y registra la fecha y hora de recogida del vehículo.",
            parameters = @Parameter(name = "id", description = "ID de la cita", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cita marcada como completada", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Cita no encontrada", content = @Content())
            }
    )
    @PutMapping("/{id}/complete")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<AppointmentResponse> markAsCompleted(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.markAsCompleted(id));
    }

}
