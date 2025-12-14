package com.airtribe.meditrack.utils;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.InvalidDataException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
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
                appointment.getPatient() != null &&
                appointment.getPatient().getAssignedDoctors() != null &&
                isFutureDate(appointment.getAppointmentDateTime());
    }

    public static boolean isValidBill(Bill bill) {
        return bill != null &&
                isPositive(bill.getId()) &&
                bill.getAppointment() != null &&
                bill.getAppointment().getPatient() != null &&
                bill.getAppointment().getPatient().getAssignedDoctors() != null &&
                bill.getAppointment().getStatus() != null &&
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

    // Advanced Java 8: Predicate-based validators
    public static final Predicate<String> IS_NOT_EMPTY = value -> value != null && !value.trim().isEmpty();
    public static final Predicate<Long> IS_POSITIVE_LONG = value -> value > 0;
    public static final Predicate<Double> IS_POSITIVE_DOUBLE = value -> value > 0;
    public static final Predicate<String> IS_VALID_MOBILE = mobile -> mobile != null && Pattern.matches("\\d{10}", mobile);
    public static final Predicate<String> IS_VALID_EMAIL = email -> email != null && Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", email);
    public static final Predicate<LocalDateTime> IS_FUTURE_DATE = dateTime -> dateTime != null && dateTime.isAfter(LocalDateTime.now());

    // Advanced Java 8: Composite validator using predicate chaining
    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }

    public static <T> void validateWithPredicate(T value, Predicate<T> predicate, String errorMessage) {
        if (!predicate.test(value)) {
            throw new InvalidDataException(errorMessage);
        }
    }

    // Advanced Java 8: Builder-style validation
    public static class ValidationBuilder<T> {
        private final T value;
        private final List<Predicate<T>> predicates = new ArrayList<>();
        private String errorMessage = "Validation failed";

        public ValidationBuilder(T value) {
            this.value = value;
        }

        public ValidationBuilder<T> must(Predicate<T> predicate) {
            predicates.add(predicate);
            return this;
        }

        public ValidationBuilder<T> withMessage(String message) {
            this.errorMessage = message;
            return this;
        }

        public void validate() {
            boolean isValid = predicates.stream().allMatch(p -> p.test(value));
            if (!isValid) {
                throw new InvalidDataException(errorMessage);
            }
        }
    }

    public static <T> ValidationBuilder<T> validate(T value) {
        return new ValidationBuilder<>(value);
    }
}
