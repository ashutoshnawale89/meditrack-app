package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.AppointmentStatus;

import java.time.LocalDateTime;

public class Appointment {

    private long id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime appointmentDateTime;
    private AppointmentStatus status;
    private String notes;

    public Appointment() {
        this.status = AppointmentStatus.SCHEDULED;
    }

    public Appointment(long id, Doctor doctor, Patient patient, LocalDateTime appointmentDateTime) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDateTime = appointmentDateTime;
        this.status = AppointmentStatus.SCHEDULED;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) { this.appointmentDateTime = appointmentDateTime; }

    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

}
