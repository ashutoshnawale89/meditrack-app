package com.airtribe.meditrack.service;

import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.utils.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
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
                .filter(a -> a.getPatient().getAssignedDoctors().getId() == doctor.getId())
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

    // Advanced Java 8: Find appointments by predicate
    public List<Appointment> findAppointmentsByPredicate(Predicate<Appointment> predicate) {
        return appointments.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Get appointments for specific date
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return appointments.stream()
                .filter(a -> a.getAppointmentDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Get upcoming appointments
    public List<Appointment> getUpcomingAppointments() {
        return appointments.stream()
                .filter(a -> a.getAppointmentDateTime().isAfter(LocalDateTime.now()))
                .filter(a -> a.getStatus() == AppointmentStatus.SCHEDULED)
                .sorted(Comparator.comparing(Appointment::getAppointmentDateTime))
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Count appointments by status
    public Map<AppointmentStatus, Long> countAppointmentsByStatus() {
        return appointments.stream()
                .collect(Collectors.groupingBy(
                        Appointment::getStatus,
                        Collectors.counting()
                ));
    }

    // Advanced Java 8: Group appointments by date
    public Map<LocalDate, List<Appointment>> groupAppointmentsByDate() {
        return appointments.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getAppointmentDateTime().toLocalDate()
                ));
    }

    // Advanced Java 8: Get appointment statistics
    public Map<String, Long> getAppointmentStatistics() {
        long total = appointments.size();
        long scheduled = appointments.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.SCHEDULED)
                .count();
        long completed = appointments.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.COMPLETED)
                .count();
        long canceled = appointments.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.CANCELED)
                .count();

        Map<String, Long> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("scheduled", scheduled);
        stats.put("completed", completed);
        stats.put("canceled", canceled);
        return stats;
    }

    // Advanced Java 8: Check for appointment conflicts
    public boolean hasConflictingAppointment(Doctor doctor, LocalDateTime dateTime, long durationMinutes) {
        LocalDateTime endTime = dateTime.plusMinutes(durationMinutes);
        
        return appointments.stream()
                .filter(a -> a.getPatient().getAssignedDoctors().getId() == doctor.getId())
                .filter(a -> a.getStatus() == AppointmentStatus.SCHEDULED)
                .anyMatch(a -> {
                    LocalDateTime existingStart = a.getAppointmentDateTime();
                    LocalDateTime existingEnd = existingStart.plusMinutes(durationMinutes);
                    return dateTime.isBefore(existingEnd) && endTime.isAfter(existingStart);
                });
    }
}
