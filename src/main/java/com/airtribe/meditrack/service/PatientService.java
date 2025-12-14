package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.interfaces.Searchable;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PatientService implements Searchable<Patient> {

    private List<Patient> patients = new ArrayList<>();

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public boolean removePatient(long patientId) {
        return patients.removeIf(p -> p.getId() == patientId);
    }

    public boolean updatePatient(long patientId, Patient updatedPatient) {
        boolean exists = patients.stream().anyMatch(p -> p.getId() == patientId);
        if (exists) {
            patients.replaceAll(p -> p.getId() == patientId ? updatedPatient : p);
        }
        return exists;
    }

    public List<Patient> getAllPatients() {
        return patients;
    }

    public boolean assignDoctorToPatient(long patientId, Doctor doctor) {
        Optional<Patient> patientOpt = findById(patientId);
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            // If you keep Set<Doctor> in Patient
            patient.setAssignedDoctors(doctor);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Patient> findById(long patientId) {
        return patients.stream()
                .filter(p -> p.getId() == patientId)
                .findFirst();
    }

    @Override
    public List<Patient> findByName(String name) {
        return patients.stream()
                .filter(p -> p.getPerson().getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Find patients by predicate
    public List<Patient> findPatientsByPredicate(Predicate<Patient> predicate) {
        return patients.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Find active patients
    public List<Patient> findActivePatients() {
        return patients.stream()
                .filter(Patient::isActive)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Get patient statistics
    public Map<String, Long> getPatientStatistics() {
        long totalPatients = patients.size();
        long activePatients = patients.stream().filter(Patient::isActive).count();
        long inactivePatients = totalPatients - activePatients;
        
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", totalPatients);
        stats.put("active", activePatients);
        stats.put("inactive", inactivePatients);
        return stats;
    }

    // Advanced Java 8: Find patients by age range
    public List<Patient> findPatientsByAgeRange(int minAge, int maxAge) {
        return patients.stream()
                .filter(p -> p.getPerson().getAge() >= minAge && p.getPerson().getAge() <= maxAge)
                .collect(Collectors.toList());
    }
}