package com.airtribe.meditrack.service;

import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.utils.Validator;

import java.time.DayOfWeek;
import java.util.*;
import java.util.function.Predicate;
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

    // Advanced Java 8: Find doctors using custom predicate
    public List<Doctor> findDoctorsByPredicate(Predicate<Doctor> predicate) {
        return doctors.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Get average experience
    public OptionalDouble getAverageExperience() {
        return doctors.stream()
                .mapToInt(Doctor::getExperience)
                .average();
    }

    // Advanced Java 8: Group doctors by specialization
    public Map<Specialization, List<Doctor>> groupDoctorsBySpecialization() {
        return doctors.stream()
                .collect(Collectors.groupingBy(Doctor::getSpecialization));
    }

    // Advanced Java 8: Find most experienced doctors
    public List<Doctor> findTopExperiencedDoctors(int limit) {
        return doctors.stream()
                .sorted(Comparator.comparingInt(Doctor::getExperience).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Count doctors by specialization
    public Map<Specialization, Long> countDoctorsBySpecialization() {
        return doctors.stream()
                .collect(Collectors.groupingBy(
                        Doctor::getSpecialization,
                        Collectors.counting()
                ));
    }
}
