package com.airtribe.meditrack.service;

import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.utils.Validator;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

public class DoctorService implements Searchable<Doctor> {

    private final List<Doctor> doctors = new ArrayList<>();

    public void addDoctor(Doctor doctor) {
        Validator.validateDoctor(doctor);
        doctors.add(doctor);
    }


    public boolean removeDoctor(long doctorId) {
        return doctors.removeIf(d -> d.getId() == doctorId);
    }

    public boolean updateDoctor(long doctorId, Doctor updatedDoctor) {
        boolean exists = doctors.stream().anyMatch(d -> d.getId() == doctorId);
        if (exists) {
            doctors.replaceAll(d -> d.getId() == doctorId ? updatedDoctor : d);
        }
        return exists;
    }

    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors);
    }

    @Override
    public Optional<Doctor> findById(long id) {
        return doctors.stream()
                .filter(d -> d.getId() == id)
                .findFirst();
    }

    @Override
    public List<Doctor> findByName(String name) {
        return doctors.stream()
                .filter(d -> d.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }


    public List<Doctor> findDoctorsBySpecialization(Specialization specialization) {
        return doctors.stream()
                .filter(d -> d.getSpecialization() == specialization)
                .collect(Collectors.toList());
    }

    public List<Doctor> findDoctorsByAvailability(DayOfWeek day) {
        return doctors.stream()
                .filter(d -> d.getAvailableDays() != null)
                .filter(d -> d.getAvailableDays().contains(day))
                .collect(Collectors.toList());
    }
}
