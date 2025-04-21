package com.stiven.taller.service;

import com.stiven.taller.exception.ResourceNotFoundException;
import com.stiven.taller.model.cliente.Cliente;
import com.stiven.taller.repository.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    public Cliente createClient(Cliente client) {
        return clienteRepository.save(client);
    }

    public List<Cliente> getAllClients() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> getClientById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente updateClient(Long id, Cliente client) {
        if (clienteRepository.existsById(id)) {
            client.setId(id);
            return clienteRepository.save(client);
        }
        return null;
    }

    public boolean deleteClient(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Cliente getClientByCedula(String cedula) {
        return clienteRepository.findByCedula(cedula)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con cédula: " + cedula));
    }

}

