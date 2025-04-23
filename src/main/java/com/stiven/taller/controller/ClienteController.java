package com.stiven.taller.controller;

import com.stiven.taller.dto.ClienteRequest;
import com.stiven.taller.dto.ClienteResponse;
import com.stiven.taller.model.cliente.Cliente;
import com.stiven.taller.service.ClienteService;
import com.stiven.taller.service.VehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@Tag(name = "Gestión de Clientes", description = "Operaciones relacionadas con los clientes registrados en el sistema.")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VehiculoService vehiculoService;

    @Operation(
            summary = "Registrar nuevo cliente",
            description = "Registra un nuevo cliente en el sistema. La cédula debe ser única."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente registrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "cedula": "1234567890",
                                      "nombre": "Juan",
                                      "apellido": "Pérez",
                                      "email": "juan.perez@email.com",
                                      "telefono": "0999999999",
                                      "direccion": "Av. Siempre Viva 123",
                                      "fechaRegistro": "2024-04-20",
                                      "vehiculos": []
                                    }
                                    """)
                    )),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o cédula ya registrada", content = @Content())
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<ClienteResponse> createClient(@RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.createClient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Obtener todos los clientes",
            description = "Devuelve una lista completa de los clientes registrados."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de clientes",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = ClienteResponse.class)
                    ),
                    examples = @ExampleObject(
                            name = "Ejemplo de respuesta",
                            value = "[\n" +
                                    "  {\n" +
                                    "    \"cedula\": \"1234567890\",\n" +
                                    "    \"nombre\": \"Juan\",\n" +
                                    "    \"apellido\": \"Pérez\",\n" +
                                    "    \"email\": \"juan.perez@example.com\",\n" +
                                    "    \"telefono\": \"3101234567\",\n" +
                                    "    \"direccion\": \"Calle 123 #45-67\",\n" +
                                    "    \"fechaRegistro\": \"2024-05-01\",\n" +
                                    "    \"vehiculos\": []\n" +
                                    "  },\n" +
                                    "  {\n" +
                                    "    \"cedula\": \"9876543210\",\n" +
                                    "    \"nombre\": \"María\",\n" +
                                    "    \"apellido\": \"Gómez\",\n" +
                                    "    \"email\": \"maria.gomez@example.com\",\n" +
                                    "    \"telefono\": \"3007654321\",\n" +
                                    "    \"direccion\": \"Carrera 45 #12-34\",\n" +
                                    "    \"fechaRegistro\": \"2024-06-10\",\n" +
                                    "    \"vehiculos\": [\n" +
                                    "      {\n" +
                                    "        \"placa\": \"XYZ123\",\n" +
                                    "        \"marca\": \"Toyota\",\n" +
                                    "        \"modelo\": \"Corolla\",\n" +
                                    "        \"anio\": 2020,\n" +
                                    "        \"color\": \"Gris\",\n" +
                                    "        \"clienteCedula\": \"9876543210\"\n" +
                                    "      }\n" +
                                    "    ]\n" +
                                    "  }\n" +
                                    "]"
                    )
            )
    )
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<ClienteResponse>> getAllClients() {
        List<ClienteResponse> clients = clienteService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }


    @Operation(
            summary = "Buscar cliente por ID",
            description = "Busca un cliente específico usando su identificador interno (ID)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cliente.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content())
    })
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ClienteResponse> getClientById(@PathVariable Long id) {
        ClienteResponse response = clienteService.getClientById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Actualizar un cliente existente",
            description = "Actualiza la información de un cliente mediante su ID.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del cliente a actualizar",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteRequest.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de actualización",
                                    value = "{\n" +
                                            "  \"nombre\": \"Juan\",\n" +
                                            "  \"apellido\": \"Pérez\",\n" +
                                            "  \"email\": \"juan.perez@example.com\",\n" +
                                            "  \"telefono\": \"3101234567\",\n" +
                                            "  \"direccion\": \"Calle 123 #45-67\",\n" +
                                            "  \"fechaRegistro\": \"2024-05-01\"\n" +
                                            "}"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClienteResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content())
    })
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ClienteResponse> updateClient(@PathVariable Long id, @RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.updateClient(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina a un cliente del sistema mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content())
    })
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clienteService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Buscar cliente por cédula",
            description = "Busca un cliente usando su número de cédula."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteResponse.class),
                            examples = @ExampleObject(value = """
                            {
                              "cedula": "1234567890",
                              "nombre": "Juan",
                              "apellido": "Pérez",
                              "email": "juan.perez@email.com",
                              "telefono": "0999999999",
                              "direccion": "Av. Siempre Viva 123",
                              "fechaRegistro": "2024-04-20",
                              "vehiculos": ["ABC123"]
                            }
                            """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content())
    })
    @GetMapping("/cedula/{cedula}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ClienteResponse> getClientByCedula(@PathVariable String cedula) {
        ClienteResponse response = clienteService.getClientByCedula(cedula);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
