package com.stiven.taller.repository.vehicle;

import com.stiven.taller.dto.VehicleResponse;
import com.stiven.taller.model.cliente.Cliente;
import com.stiven.taller.model.vehiculo.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    boolean existsByPlaca(String placa);
    Optional<Vehiculo> findByPlaca(String placa);
    List<Vehiculo> findByCliente(Cliente cliente);

    @Query("SELECT new com.stiven.taller.dto.VehicleResponse(v.placa, v.marca, v.modelo, v.anio, v.color, v.cliente.cedula) " +
            "FROM Vehiculo v " +
            "WHERE v.placa = :placa")
    Optional<VehicleResponse> findVehicleResponseByPlaca(@Param("placa") String placa);

}
