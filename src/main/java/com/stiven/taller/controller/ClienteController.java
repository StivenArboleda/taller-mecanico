package com.stiven.taller.controller;

import com.stiven.taller.model.cliente.Cliente;
import com.stiven.taller.service.ClienteService;
import com.stiven.taller.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping
    public ResponseEntity<Cliente> createClient(@RequestBody Cliente client) {
        Cliente createdClient = clienteService.createClient(client);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClients() {
        List<Cliente> clients = clienteService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClientById(@PathVariable Long id) {
        Optional<Cliente> client = clienteService.getClientById(id);
        if (client.isPresent()) {
            return new ResponseEntity<>(client.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateClient(@PathVariable Long id, @RequestBody Cliente client) {
        Cliente updatedClient = clienteService.updateClient(id, client);
        if (updatedClient != null) {
            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        if (clienteService.deleteClient(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<Cliente> getClientByCedula(@PathVariable String cedula) {
        Cliente cliente = clienteService.getClientByCedula(cedula);
        return ResponseEntity.ok(cliente);
    }

}
