package com.stiven.taller.service;

import com.stiven.taller.dto.AppointmentRequest;
import com.stiven.taller.dto.AppointmentResponse;
import com.stiven.taller.enums.AppointmentStatus;
import com.stiven.taller.model.appointment.Appointment;
import com.stiven.taller.model.cliente.Cliente;
import com.stiven.taller.model.vehiculo.Vehiculo;
import com.stiven.taller.repository.appointment.AppointmentRepository;
import com.stiven.taller.repository.cliente.ClienteRepository;
import com.stiven.taller.repository.vehicle.VehiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ClienteRepository clientRepository;

    @Autowired
    private VehiculoRepository vehicleRepository;

    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Cliente client = clientRepository.findById(request.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        Vehiculo vehicle = vehicleRepository.findByPlaca(request.getVehiculoPlaca())
                .orElseThrow(() -> new EntityNotFoundException("Vehículo no encontrado"));

        Appointment appointment = new Appointment();
        appointment.setFechaCita(request.getFechaCita());
        appointment.setMotivo(request.getMotivo());
        appointment.setEstado(AppointmentStatus.valueOf(request.getEstado().toUpperCase()));
        appointment.setCliente(client);
        appointment.setVehiculo(vehicle);

        Appointment saved = appointmentRepository.save(appointment);

        return mapToResponse(saved);
    }

    public AppointmentResponse updateAppointmentStatus(Long id, String newStatus) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cita no encontrada"));

        AppointmentStatus status = AppointmentStatus.valueOf(newStatus.toUpperCase());
        appointment.setEstado(status);

        if (status == AppointmentStatus.COMPLETADA) {
            appointment.setFechaRecogida(LocalDateTime.now());
        }

        Appointment updated = appointmentRepository.save(appointment);

        return mapToResponse(updated);
    }

    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cita no encontrada"));
        return mapToResponse(appointment);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .fechaCita(appointment.getFechaCita())
                .motivo(appointment.getMotivo())
                .estado(appointment.getEstado().name())
                .fechaRecogida(appointment.getFechaRecogida())
                .clienteNombre(appointment.getCliente().getNombre() + " " + appointment.getCliente().getApellido())
                .vehiculoPlaca(appointment.getVehiculo().getPlaca())
                .build();
    }

    public AppointmentResponse markAsCompleted(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cita no encontrada"));

        // Cambiar estado a COMPLETADA y establecer fecha de recogida
        appointment.setEstado(AppointmentStatus.COMPLETADA);
        appointment.setFechaRecogida(LocalDateTime.now());

        Appointment updated = appointmentRepository.save(appointment);

        return mapToResponse(updated);
    }

}
