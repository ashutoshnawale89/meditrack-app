package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.interfaces.Searchable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<Patient> result = new ArrayList<>();
        patients.stream()
                .filter(p -> p.getPerson().getName().equalsIgnoreCase(name))
                .forEach(result::add);
        return result;
    }
}

