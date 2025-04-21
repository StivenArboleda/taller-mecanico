package com.stiven.taller.repository.appointment;

import com.stiven.taller.model.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
