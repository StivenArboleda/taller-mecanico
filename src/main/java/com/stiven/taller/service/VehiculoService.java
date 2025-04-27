package com.stiven.taller.service;

import com.stiven.taller.dto.VehicleRequest;
import com.stiven.taller.dto.VehicleResponse;
import com.stiven.taller.exception.ResourceNotFoundException;
import com.stiven.taller.model.cliente.Cliente;
import com.stiven.taller.model.vehiculo.Vehiculo;
import com.stiven.taller.repository.cliente.ClienteRepository;
import com.stiven.taller.repository.vehicle.VehiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehicleRepository;
    private final ClienteRepository clientRepository;


    public VehicleResponse createVehicle(VehicleRequest request) {
        Cliente cliente = clientRepository.findByCedula(request.getClienteCedula())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con cédula: " + request.getClienteCedula()));

        Vehiculo vehiculo = Vehiculo.builder()
                .placa(request.getPlaca())
                .marca(request.getMarca())
                .modelo(request.getModelo())
                .anio(request.getAnio())
                .color(request.getColor())
                .cliente(cliente)
                .build();

        vehiculo = vehicleRepository.save(vehiculo);

        return mapToResponse(vehiculo);
    }


    public List<VehicleResponse> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public VehicleResponse getVehicleById(Long id) {
        Vehiculo vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        return mapToResponse(vehicle);
    }

    public VehicleResponse updateVehicle(String placa, VehicleRequest request) {
        Vehiculo vehicle = vehicleRepository.findByPlaca(placa)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con placa: " + placa));

        Cliente client = clientRepository.findByCedula(request.getClienteCedula())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con cédula: " + request.getClienteCedula()));

        vehicle.setMarca(request.getMarca());
        vehicle.setModelo(request.getModelo());
        vehicle.setAnio(request.getAnio());
        vehicle.setColor(request.getColor());
        vehicle.setCliente(client);

        vehicleRepository.save(vehicle);

        return mapToResponse(vehicle);
    }


    public void deleteVehicle(String placa) {
        Vehiculo vehicle = vehicleRepository.findByPlaca(placa)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        vehicleRepository.delete(vehicle);
    }

    public VehicleResponse getVehicleByPlaca(String placa) {
        return vehicleRepository.findVehicleResponseByPlaca(placa)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con placa: " + placa));
    }

    public List<VehicleResponse> getVehiculosByCliente(String clienteCedula) {
        Cliente cliente = clientRepository.findByCedula(clienteCedula)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        List<Vehiculo> vehiculos = vehicleRepository.findByCliente(cliente);

        if (vehiculos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron vehículos para este cliente");
        }

        return vehiculos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private VehicleResponse mapToResponse(Vehiculo vehiculo) {
        return VehicleResponse.builder()
                .placa(vehiculo.getPlaca())
                .marca(vehiculo.getMarca())
                .modelo(vehiculo.getModelo())
                .anio(vehiculo.getAnio())
                .color(vehiculo.getColor())
                .clienteCedula(vehiculo.getCliente().getCedula())
                .build();
    }

}
