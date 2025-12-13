package com.airtribe.meditrack.utils;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.InvalidDataException;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class Validator {

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isPositive(long value) {
        return value > 0;
    }

    public static boolean isPositive(double value) {
        return value > 0;
    }

    public static boolean isValidMobile(String mobile) {
        return mobile != null && Pattern.matches("\\d{10}", mobile);
    }

    public static boolean isValidEmail(String email) {
        return email != null &&
                Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", email);
    }

    public static boolean isFutureDate(LocalDateTime dateTime) {
        return dateTime != null && dateTime.isAfter(LocalDateTime.now());
    }

    public static boolean isValidPatient(Patient patient) {
        return patient != null &&
                isPositive(patient.getId()) &&
                patient.getPerson() != null &&
                isNotEmpty(patient.getPerson().getName()) &&
                isValidMobile(patient.getPerson().getMobileNo()) &&
                isNotEmpty(patient.getMedicalRecordNumber());
    }

    public static boolean isValidDoctor(Doctor doctor) {
        return doctor != null &&
                isPositive(doctor.getId()) &&
                isNotEmpty(doctor.getName()) &&
                isPositive(doctor.getExperience()) &&
                doctor.getSpecialization() != null;
    }

    public static boolean isValidAppointment(Appointment appointment) {
        return appointment != null &&
                isPositive(appointment.getId()) &&
                appointment.getDoctor() != null &&
                appointment.getPatient() != null &&
                isFutureDate(appointment.getAppointmentDateTime());
    }

    public static boolean isValidBill(Bill bill) {
        return bill != null &&
                isPositive(bill.getId()) &&
                bill.getAppointment() != null &&
                bill.getAppointment().getDoctor() != null &&
                bill.getAppointment().getPatient() != null &&
                isPositive(bill.getAmount());
    }

    public static void validatePatient(Patient patient) {
        if (!isValidPatient(patient)) {
            throw new InvalidDataException("Invalid patient data");
        }
    }

    public static void validateDoctor(Doctor doctor) {
        if (!isValidDoctor(doctor)) {
            throw new InvalidDataException("Invalid doctor data");
        }
    }

    public static void validateAppointment(Appointment appointment) {
        if (!isValidAppointment(appointment)) {
            throw new InvalidDataException("Invalid appointment data");
        }
    }

    public static void validateBill(Bill bill) {
        if (!isValidBill(bill)) {
            throw new InvalidDataException("Invalid bill data");
        }
    }
}
