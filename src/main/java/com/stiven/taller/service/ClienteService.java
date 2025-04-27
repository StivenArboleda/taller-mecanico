package com.stiven.taller.service;

import com.stiven.taller.dto.ClienteRequest;
import com.stiven.taller.dto.ClienteResponse;
import com.stiven.taller.exception.BadRequestException;
import com.stiven.taller.exception.ResourceNotFoundException;
import com.stiven.taller.model.cliente.Cliente;
import com.stiven.taller.model.vehiculo.Vehiculo;
import com.stiven.taller.repository.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    public ClienteResponse createClient(ClienteRequest request) {
        if (clienteRepository.existsByCedula(request.getCedula())) {
            throw new BadRequestException("La cédula ya está registrada");
        }

        if (clienteRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }

        if (clienteRepository.existsByTelefono(request.getTelefono())) {
            throw new BadRequestException("El télefono ya pertenece a otro cliente");

        }

        Cliente cliente = Cliente.builder()
                .cedula(request.getCedula())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .fechaRegistro(new Date())
                .build();

        cliente = clienteRepository.save(cliente);

        return mapToResponse(cliente);
    }


    public List<ClienteResponse> getAllClients() {
        return clienteRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ClienteResponse updateClient(String cedula, ClienteRequest request) {
        Cliente cliente = clienteRepository.findByCedula(cedula)
                .orElseThrow(() -> new BadRequestException("Cliente no encontrado con ID: " + cedula));

        Long clienteId = cliente.getId();

        clienteRepository.findByEmail(request.getEmail()).ifPresent(existing -> {
            if (!existing.getId().equals(clienteId)) {
                throw new BadRequestException("El email ya está registrado por otro cliente");
            }
        });

        clienteRepository.findByTelefono(request.getTelefono()).ifPresent(existing -> {
            if (!existing.getId().equals(clienteId)) {
                throw new BadRequestException("El teléfono ya pertenece a otro cliente");
            }
        });

        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());

        cliente = clienteRepository.save(cliente);

        return mapToResponse(cliente);
    }


    public void deleteClient(String cedula) {
        Cliente cliente = clienteRepository.findByCedula(cedula)
                .orElseThrow(() -> new BadRequestException("Cliente no encontrado con ID: " + cedula));
        clienteRepository.delete(cliente);
    }

    public ClienteResponse getClientByCedula(String cedula) {
        Cliente cliente = clienteRepository.findByCedula(cedula)
                .orElseThrow(() -> new BadRequestException("Cliente no encontrado con cédula: " + cedula));
        return mapToResponse(cliente);
    }

    private ClienteResponse mapToResponse(Cliente cliente) {
        List<String> placasVehiculos = Optional.ofNullable(cliente.getVehiculos())
                .orElse(new ArrayList<>())
                .stream()
                .map(Vehiculo::getPlaca)
                .collect(Collectors.toList());

        return ClienteResponse.builder()
                .cedula(cliente.getCedula())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())
                .fechaRegistro(cliente.getFechaRegistro())
                .vehiculos(placasVehiculos)
                .build();
    }


}

