package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

public class Patient {

    private long id;
    private LocalDateTime registrationDate;
    private Person person;
    private Doctor assignedDoctors ;
    private String medicalRecordNumber;
    private boolean isActive;

    public Patient() {
        this.registrationDate = LocalDateTime.now();
        this.isActive = true;
    }

    public Patient(long id, Person person, String medicalRecordNumber) {
        this.id = id;
        this.person = person;
        this.medicalRecordNumber = medicalRecordNumber;
        this.registrationDate = LocalDateTime.now();
        this.isActive = true;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

    public String getMedicalRecordNumber() { return medicalRecordNumber; }
    public void setMedicalRecordNumber(String medicalRecordNumber) { this.medicalRecordNumber = medicalRecordNumber; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public Doctor getAssignedDoctors() { return assignedDoctors; }
    public void setAssignedDoctors(Doctor assignedDoctors) {
        this.assignedDoctors = assignedDoctors;
    }

    @Override
    public String toString() {
        return "Patient ID: " + id +
               ", Name: " + person.getName() +
               ", Medical Record Number: " + medicalRecordNumber +
               ", Registration Date: " + registrationDate +
               ", Active: " + isActive +
               ", Assigned Doctors: " + (assignedDoctors != null ? assignedDoctors.getName() : "None");
    }
}
