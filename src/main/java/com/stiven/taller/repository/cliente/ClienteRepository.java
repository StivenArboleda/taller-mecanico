package com.stiven.taller.repository.cliente;

import com.stiven.taller.model.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCedula(String cedula);
    boolean existsByCedula(String cedula);
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByTelefono(String telefono);


}