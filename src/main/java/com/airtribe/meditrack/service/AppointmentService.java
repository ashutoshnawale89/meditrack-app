package com.airtribe.meditrack.service;

import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.utils.Validator;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentService implements Searchable<Appointment> {

    private final List<Appointment> appointments = new ArrayList<>();

    public void bookAppointment(Appointment appointment) {
        Validator.validateAppointment(appointment);
        appointments.add(appointment);
    }

    public void cancelAppointment(long appointmentId) {
        Appointment appointment = findById(appointmentId)
                .orElseThrow(() ->
                        new AppointmentNotFoundException(
                                "Appointment not found with ID: " + appointmentId));

        appointment.setStatus(AppointmentStatus.CANCELED);
    }

    public void updateAppointment(long appointmentId,
                                  LocalDateTime newDateTime,
                                  String newNotes) {

        Appointment appointment = findById(appointmentId)
                .orElseThrow(() ->
                        new AppointmentNotFoundException(
                                "Cannot update. Appointment not found with ID: " + appointmentId));

        appointment.setAppointmentDateTime(newDateTime);
        appointment.setNotes(newNotes);
    }

    @Override
    public Optional<Appointment> findById(long id) {
        return appointments.stream()
                .filter(a -> a.getId() == id)
                .findFirst();
    }

    @Override
    public List<Appointment> findByName(String patientName) {
        return appointments.stream()
                .filter(a -> a.getPatient() != null)
                .filter(a -> a.getPatient().getPerson().getName()
                        .equalsIgnoreCase(patientName))
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByPatient(Patient patient) {
        return appointments.stream()
                .filter(a -> a.getPatient().getId() == patient.getId())
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByDoctor(Doctor doctor) {
        return appointments.stream()
                .filter(a -> a.getDoctor().getId() == doctor.getId())
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointments.stream()
                .filter(a -> a.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }
}
