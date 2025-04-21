package com.stiven.taller.service;

import com.stiven.taller.dto.ClienteRequest;
import com.stiven.taller.dto.ClienteResponse;
import com.stiven.taller.exception.ResourceNotFoundException;
import com.stiven.taller.model.cliente.Cliente;
import com.stiven.taller.model.vehiculo.Vehiculo;
import com.stiven.taller.repository.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Cliente cliente = Cliente.builder()
                .cedula(request.getCedula())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .fechaRegistro(new Date()) // Asignar fecha actual
                .build();

        cliente = clienteRepository.save(cliente);

        return mapToResponse(cliente);
    }


    public List<ClienteResponse> getAllClients() {
        return clienteRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ClienteResponse getClientById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        return mapToResponse(cliente);
    }

    public ClienteResponse updateClient(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));

        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());

        cliente = clienteRepository.save(cliente);

        return mapToResponse(cliente);
    }

    public void deleteClient(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        clienteRepository.delete(cliente);
    }

    public ClienteResponse getClientByCedula(String cedula) {
        Cliente cliente = clienteRepository.findByCedula(cedula)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con cédula: " + cedula));
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

