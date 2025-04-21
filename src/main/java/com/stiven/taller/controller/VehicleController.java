package com.stiven.taller.controller;

import com.stiven.taller.dto.VehicleRequest;
import com.stiven.taller.dto.VehicleResponse;
import com.stiven.taller.model.vehiculo.Vehiculo;
import com.stiven.taller.service.ClienteService;
import com.stiven.taller.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehiculoService vehicleService;

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<VehicleResponse> create(@RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.createVehicle(request));
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAll() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @PutMapping("/{placa}")
    public ResponseEntity<VehicleResponse> update(@PathVariable String placa, @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.updateVehicle(placa, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<Vehiculo> getVehicleByPlaca(@PathVariable String placa) {
        Vehiculo vehiculo = vehicleService.getVehicleByPlaca(placa);
        return ResponseEntity.ok(vehiculo);
    }

    @GetMapping("/cliente/{clienteCedula}")
    public ResponseEntity<List<VehicleResponse>> getVehiculosByCliente(@PathVariable String clienteCedula) {
        List<VehicleResponse> vehiculos = vehicleService.getVehiculosByCliente(clienteCedula);
        return ResponseEntity.ok(vehiculos);
    }

}
