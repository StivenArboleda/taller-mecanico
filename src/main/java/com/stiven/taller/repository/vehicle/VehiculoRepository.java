package com.stiven.taller.repository.vehicle;

import com.stiven.taller.model.cliente.Cliente;
import com.stiven.taller.model.vehiculo.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    boolean existsByPlaca(String placa);
    Optional<Vehiculo> findByPlaca(String placa);
    List<Vehiculo> findByCliente(Cliente cliente);

}
