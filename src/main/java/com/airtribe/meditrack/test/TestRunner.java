package com.airtribe.meditrack.test;

import com.airtribe.meditrack.constants.*;
import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.service.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Manual Test Runner for MediTrack Application.
 * 
 * <p>This class provides comprehensive automated testing functionality for all
 * MediTrack features including doctors, patients, appointments, and billing.</p>
 * 
 * <p>Usage:
 * <pre>
 *     java com.airtribe.meditrack.test.TestRunner
 *     java com.airtribe.meditrack.test.TestRunner --test=all
 *     java com.airtribe.meditrack.test.TestRunner --test=doctors
 * </pre>
 * </p>
 * 
 * @author MediTrack Development Team
 * @version 1.0
 * @since 2025-12-14
 */
public class TestRunner {

    private static final DoctorService doctorService = new DoctorService();
    private static final PatientService patientService = new PatientService();
    private static final AppointmentService appointmentService = new AppointmentService();
    private static final BillService billService = new BillService();

    private static int testsPassed = 0;
    private static int testsFailed = 0;
    private static int testsTotal = 0;

    /**
     * Main entry point for the test runner.
     * 
     * @param args Command line arguments. Supports:
     *             --test=all (default): Run all tests
     *             --test=doctors: Test doctor functionality only
     *             --test=patients: Test patient functionality only
     *             --test=appointments: Test appointment functionality only
     *             --test=bills: Test billing functionality only
     *             --verbose: Enable detailed output
     */
    public static void main(String[] args) {
        printHeader();
        
        String testSuite = "all";
        boolean verbose = false;
        
        // Parse command line arguments
        for (String arg : args) {
            if (arg.startsWith("--test=")) {
                testSuite = arg.substring(7);
            } else if (arg.equals("--verbose") || arg.equals("-v")) {
                verbose = true;
            } else if (arg.equals("--help") || arg.equals("-h")) {
                printHelp();
                return;
            }
        }
        
        System.out.println("Running test suite: " + testSuite.toUpperCase());
        System.out.println("Verbose mode: " + (verbose ? "ON" : "OFF"));
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        try {
            switch (testSuite.toLowerCase()) {
                case "doctors" -> testDoctorFunctionality(verbose);
                case "patients" -> testPatientFunctionality(verbose);
                case "appointments" -> testAppointmentFunctionality(verbose);
                case "bills" -> testBillFunctionality(verbose);
                case "all" -> {
                    testDoctorFunctionality(verbose);
                    testPatientFunctionality(verbose);
                    testAppointmentFunctionality(verbose);
                    testBillFunctionality(verbose);
                }
                default -> System.out.println("❌ Unknown test suite: " + testSuite);
            }
            
            printSummary();
        } catch (Exception e) {
            System.err.println("❌ Fatal error during testing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Tests all doctor-related functionality including CRUD operations,
     * searching, filtering, and statistical operations.
     * 
     * @param verbose Enable detailed output for each test
     */
    private static void testDoctorFunctionality(boolean verbose) {
        System.out.println("\n🩺 Testing Doctor Functionality");
        System.out.println("-".repeat(60));
        
        // Test 1: Add Doctor
        test("Add Doctor", () -> {
            Doctor doctor = createSampleDoctor(1L, "Dr. Smith", 10, Specialization.CARDIOLOGY);
            doctorService.addDoctor(doctor);
            return doctorService.findById(1L).isPresent();
        }, verbose);
        
        // Test 2: Find Doctor by ID
        test("Find Doctor by ID", () -> {
            Optional<Doctor> doctor = doctorService.findById(1L);
            return doctor.isPresent() && doctor.get().getName().equals("Dr. Smith");
        }, verbose);
        
        // Test 3: Find Doctor by Name
        test("Find Doctor by Name", () -> {
            List<Doctor> doctors = doctorService.findByName("Dr. Smith");
            return !doctors.isEmpty() && doctors.get(0).getName().equals("Dr. Smith");
        }, verbose);
        
        // Test 4: Add Multiple Doctors
        test("Add Multiple Doctors", () -> {
            doctorService.addDoctor(createSampleDoctor(2L, "Dr. Jones", 15, Specialization.NEUROLOGY));
            doctorService.addDoctor(createSampleDoctor(3L, "Dr. Brown", 8, Specialization.PEDIATRICS));
            return doctorService.getAllDoctors().size() >= 3;
        }, verbose);
        
        // Test 5: Find by Specialization
        test("Find Doctors by Specialization", () -> {
            List<Doctor> cardiologists = doctorService.findDoctorsBySpecialization(Specialization.CARDIOLOGY);
            return !cardiologists.isEmpty();
        }, verbose);
        
        // Test 6: Find by Availability
        test("Find Doctors by Availability", () -> {
            List<Doctor> mondayDoctors = doctorService.findDoctorsByAvailability(DayOfWeek.MONDAY);
            return mondayDoctors.size() > 0;
        }, verbose);
        
        // Test 7: Get Average Experience
        test("Calculate Average Experience", () -> {
            OptionalDouble avg = doctorService.getAverageExperience();
            return avg.isPresent() && avg.getAsDouble() > 0;
        }, verbose);
        
        // Test 8: Group by Specialization
        test("Group Doctors by Specialization", () -> {
            Map<Specialization, List<Doctor>> grouped = doctorService.groupDoctorsBySpecialization();
            return !grouped.isEmpty();
        }, verbose);
        
        // Test 9: Find Top Experienced Doctors
        test("Find Top Experienced Doctors", () -> {
            List<Doctor> top = doctorService.findTopExperiencedDoctors(2);
            return !top.isEmpty() && top.size() <= 2;
        }, verbose);
        
        // Test 10: Update Doctor
        test("Update Doctor", () -> {
            Doctor updated = createSampleDoctor(1L, "Dr. Smith Updated", 11, Specialization.CARDIOLOGY);
            return doctorService.updateDoctor(1L, updated);
        }, verbose);
    }

    /**
     * Tests all patient-related functionality including registration,
     * updates, searching, and statistics.
     * 
     * @param verbose Enable detailed output for each test
     */
    private static void testPatientFunctionality(boolean verbose) {
        System.out.println("\n🏥 Testing Patient Functionality");
        System.out.println("-".repeat(60));
        
        // Test 1: Add Patient
        test("Add Patient", () -> {
            Patient patient = createSamplePatient(1L, "John Doe", 30, "1234567890");
            patientService.addPatient(patient);
            return patientService.findById(1L).isPresent();
        }, verbose);
        
        // Test 2: Find Patient by ID
        test("Find Patient by ID", () -> {
            Optional<Patient> patient = patientService.findById(1L);
            return patient.isPresent() && patient.get().getPerson().getName().equals("John Doe");
        }, verbose);
        
        // Test 3: Find Patient by Name
        test("Find Patient by Name", () -> {
            List<Patient> patients = patientService.findByName("John Doe");
            return !patients.isEmpty();
        }, verbose);
        
        // Test 4: Add Multiple Patients
        test("Add Multiple Patients", () -> {
            patientService.addPatient(createSamplePatient(2L, "Jane Smith", 25, "9876543210"));
            patientService.addPatient(createSamplePatient(3L, "Bob Johnson", 40, "5555555555"));
            return patientService.getAllPatients().size() >= 3;
        }, verbose);
        
        // Test 5: Find Active Patients
        test("Find Active Patients", () -> {
            List<Patient> active = patientService.findActivePatients();
            return active.size() > 0;
        }, verbose);
        
        // Test 6: Get Patient Statistics
        test("Get Patient Statistics", () -> {
            Map<String, Long> stats = patientService.getPatientStatistics();
            return stats.containsKey("total") && stats.get("total") > 0;
        }, verbose);
        
        // Test 7: Find by Age Range
        test("Find Patients by Age Range", () -> {
            List<Patient> inRange = patientService.findPatientsByAgeRange(20, 35);
            return inRange.size() >= 2;
        }, verbose);
        
        // Test 8: Update Patient
        test("Update Patient", () -> {
            Patient updated = createSamplePatient(1L, "John Doe Updated", 31, "1234567890");
            return patientService.updatePatient(1L, updated);
        }, verbose);
    }

    /**
     * Tests all appointment-related functionality including booking,
     * cancellation, searching, and scheduling operations.
     * 
     * @param verbose Enable detailed output for each test
     */
    private static void testAppointmentFunctionality(boolean verbose) {
        System.out.println("\n📅 Testing Appointment Functionality");
        System.out.println("-".repeat(60));
        
        // Setup: Assign doctors to patients for valid appointments
        Doctor doctor = doctorService.findById(1L).orElse(null);
        if (doctor != null) {
            patientService.assignDoctorToPatient(1L, doctor);
            patientService.assignDoctorToPatient(2L, doctor);
        }
        
        // Test 1: Book Appointment
        test("Book Appointment", () -> {
            Patient patient = patientService.findById(1L).orElse(null);
            if (patient == null || patient.getAssignedDoctors() == null) return false;
            
            Appointment appointment = new Appointment(1L, patient, LocalDateTime.now().plusDays(1));
            appointmentService.bookAppointment(appointment);
            return appointmentService.findById(1L).isPresent();
        }, verbose);
        
        // Test 2: Find Appointment by ID
        test("Find Appointment by ID", () -> {
            return appointmentService.findById(1L).isPresent();
        }, verbose);
        
        // Test 3: Get All Appointments
        test("Get All Appointments", () -> {
            return appointmentService.getAllAppointments().size() > 0;
        }, verbose);
        
        // Test 4: Book Multiple Appointments
        test("Book Multiple Appointments", () -> {
            Patient patient2 = patientService.findById(2L).orElse(null);
            if (patient2 == null || patient2.getAssignedDoctors() == null) return false;
            
            appointmentService.bookAppointment(
                new Appointment(2L, patient2, LocalDateTime.now().plusDays(2))
            );
            return appointmentService.getAllAppointments().size() >= 2;
        }, verbose);
        
        // Test 5: Get Upcoming Appointments
        test("Get Upcoming Appointments", () -> {
            List<Appointment> upcoming = appointmentService.getUpcomingAppointments();
            return upcoming.size() > 0;
        }, verbose);
        
        // Test 6: Count by Status
        test("Count Appointments by Status", () -> {
            Map<AppointmentStatus, Long> counts = appointmentService.countAppointmentsByStatus();
            return !counts.isEmpty();
        }, verbose);
        
        // Test 7: Get Statistics
        test("Get Appointment Statistics", () -> {
            Map<String, Long> stats = appointmentService.getAppointmentStatistics();
            return stats.containsKey("total") && stats.get("total") > 0;
        }, verbose);
        
        // Test 8: Cancel Appointment
        test("Cancel Appointment", () -> {
            appointmentService.cancelAppointment(1L);
            Appointment apt = appointmentService.findById(1L).orElse(null);
            return apt != null && apt.getStatus() == AppointmentStatus.CANCELED;
        }, verbose);
    }

    /**
     * Tests all billing-related functionality including bill generation,
     * payment processing, and financial analytics.
     * 
     * @param verbose Enable detailed output for each test
     */
    private static void testBillFunctionality(boolean verbose) {
        System.out.println("\n💰 Testing Bill Functionality");
        System.out.println("-".repeat(60));
        
        // Setup: Ensure we have valid appointments with patients that have assigned doctors
        Doctor doctor = doctorService.findById(1L).orElse(null);
        Patient patient1 = patientService.findById(1L).orElse(null);
        Patient patient2 = patientService.findById(2L).orElse(null);
        
        if (doctor != null) {
            if (patient1 != null) patientService.assignDoctorToPatient(1L, doctor);
            if (patient2 != null) patientService.assignDoctorToPatient(2L, doctor);
        }
        
        // Ensure we have valid appointments (they might have been canceled in previous tests)
        Appointment apt1 = appointmentService.findById(1L).orElse(null);
        if (apt1 == null && patient1 != null && patient1.getAssignedDoctors() != null) {
            apt1 = new Appointment(10L, patient1, LocalDateTime.now().plusDays(1));
            appointmentService.bookAppointment(apt1);
        }
        
        Appointment apt2 = appointmentService.findById(2L).orElse(null);
        if (apt2 == null && patient2 != null && patient2.getAssignedDoctors() != null) {
            apt2 = new Appointment(11L, patient2, LocalDateTime.now().plusDays(2));
            appointmentService.bookAppointment(apt2);
        }
        
        // Test 1: Create Bill
        test("Create Bill", () -> {
            Appointment apt = appointmentService.findById(2L).orElse(null);
            if (apt == null || apt.getPatient().getAssignedDoctors() == null) {
                // Try with the newly created appointment
                apt = appointmentService.findById(11L).orElse(null);
                if (apt == null) return false;
            }
            
            Bill bill = new Bill(1L, apt, 500.0);
            billService.createBill(bill);
            return billService.findById(1L).isPresent();
        }, verbose);
        
        // Test 2: Find Bill by ID
        test("Find Bill by ID", () -> {
            return billService.findById(1L).isPresent();
        }, verbose);
        
        // Test 3: Get All Bills
        test("Get All Bills", () -> {
            return billService.getAllBills().size() > 0;
        }, verbose);
        
        // Test 4: Create Multiple Bills
        test("Create Multiple Bills", () -> {
            Appointment aptForSecondBill = appointmentService.findById(10L).orElse(null);
            if (aptForSecondBill == null || aptForSecondBill.getPatient().getAssignedDoctors() == null) {
                // Try appointment ID 2
                aptForSecondBill = appointmentService.findById(2L).orElse(null);
                if (aptForSecondBill == null || aptForSecondBill.getPatient().getAssignedDoctors() == null) return false;
            }
            
            billService.createBill(new Bill(2L, aptForSecondBill, 750.0));
            return billService.getAllBills().size() >= 2;
        }, verbose);
        
        // Test 5: Get Bills by Status
        test("Get Bills by Status", () -> {
            List<Bill> pending = billService.getBillsByStatus(BillStatus.PENDING);
            return pending.size() > 0;
        }, verbose);
        
        // Test 6: Calculate Total by Status
        test("Calculate Total Amount by Status", () -> {
            double total = billService.getTotalAmountByStatus(BillStatus.PENDING);
            return total > 0;
        }, verbose);
        
        // Test 7: Get Statistics
        test("Get Bill Statistics", () -> {
            Map<String, Double> stats = billService.getBillStatistics();
            return stats.containsKey("total") && stats.get("total") > 0;
        }, verbose);
        
        // Test 8: Pay Bill
        test("Pay Bill", () -> {
            billService.payBill(1L);
            Bill bill = billService.findById(1L).orElse(null);
            return bill != null && billService.isBillPaid(1L);
        }, verbose);
        
        // Test 9: Get Unpaid Bills Sorted
        test("Get Unpaid Bills Sorted by Amount", () -> {
            List<Bill> unpaid = billService.getUnpaidBillsSortedByAmount();
            // Should have at least bill #2 unpaid (only bill #1 was paid)
            return unpaid.size() >= 1;
        }, verbose);
        
        // Test 10: Group Bills by Status
        test("Group Bills by Status", () -> {
            Map<BillStatus, List<Bill>> grouped = billService.groupBillsByStatus();
            return !grouped.isEmpty();
        }, verbose);
    }

    // Helper Methods

    /**
     * Creates a sample doctor for testing purposes.
     * 
     * @param id Doctor ID
     * @param name Doctor name
     * @param experience Years of experience
     * @param specialization Medical specialization
     * @return Doctor instance configured for testing
     */
    private static Doctor createSampleDoctor(Long id, String name, int experience, Specialization specialization) {
        Set<DayOfWeek> availableDays = Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        return new Doctor(id, name, experience, specialization, availableDays,
                LocalDateTime.now(), java.time.LocalTime.of(9, 0), java.time.LocalTime.of(17, 0));
    }

    /**
     * Creates a sample patient for testing purposes.
     * 
     * @param id Patient ID
     * @param name Patient name
     * @param age Patient age
     * @param mobile Mobile number
     * @return Patient instance configured for testing
     */
    private static Patient createSamplePatient(Long id, String name, int age, String mobile) {
        Person person = new Person(id, name, age, mobile);
        return new Patient(id, person, "MRN" + id);
    }

    /**
     * Executes a single test case with exception handling and result tracking.
     * 
     * @param testName Descriptive name of the test
     * @param testLogic Lambda expression containing the test logic
     * @param verbose Enable detailed output
     */
    private static void test(String testName, TestCase testLogic, boolean verbose) {
        testsTotal++;
        try {
            boolean result = testLogic.execute();
            if (result) {
                testsPassed++;
                if (verbose) {
                    System.out.println("✅ PASS: " + testName);
                }
            } else {
                testsFailed++;
                System.out.println("❌ FAIL: " + testName);
            }
        } catch (Exception e) {
            testsFailed++;
            System.out.println("❌ ERROR: " + testName + " - " + e.getMessage());
            if (verbose) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Prints the test runner header banner.
     */
    private static void printHeader() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  MediTrack Manual Test Runner v1.0");
        System.out.println("  Comprehensive Testing Suite for Healthcare Management");
        System.out.println("=".repeat(60));
    }

    /**
     * Prints the final test execution summary with statistics.
     */
    private static void printSummary() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  Test Execution Summary");
        System.out.println("=".repeat(60));
        System.out.println("Total Tests:  " + testsTotal);
        System.out.println("Passed:       " + testsPassed + " ✅");
        System.out.println("Failed:       " + testsFailed + " ❌");
        System.out.println("Success Rate: " + String.format("%.2f%%", 
                (testsPassed * 100.0) / testsTotal));
        System.out.println("=".repeat(60));
        
        if (testsFailed == 0) {
            System.out.println("\n🎉 All tests passed! System is working correctly.");
        } else {
            System.out.println("\n⚠️  Some tests failed. Please review the errors above.");
        }
    }

    /**
     * Prints help information about command-line usage.
     */
    private static void printHelp() {
        System.out.println("\nMediTrack Test Runner - Usage Guide");
        System.out.println("=".repeat(60));
        System.out.println("Usage: java com.airtribe.meditrack.test.TestRunner [options]");
        System.out.println("\nOptions:");
        System.out.println("  --test=<suite>    Specify test suite to run");
        System.out.println("                    Options: all, doctors, patients, appointments, bills");
        System.out.println("                    Default: all");
        System.out.println("  --verbose, -v     Enable verbose output");
        System.out.println("  --help, -h        Show this help message");
        System.out.println("\nExamples:");
        System.out.println("  java com.airtribe.meditrack.test.TestRunner");
        System.out.println("  java com.airtribe.meditrack.test.TestRunner --test=doctors");
        System.out.println("  java com.airtribe.meditrack.test.TestRunner --test=all --verbose");
        System.out.println("=".repeat(60));
    }

    /**
     * Functional interface for test case execution.
     * Allows lambda expressions to be used for test logic.
     */
    @FunctionalInterface
    interface TestCase {
        /**
         * Executes the test logic.
         * 
         * @return true if test passes, false otherwise
         * @throws Exception if test execution fails
         */
        boolean execute() throws Exception;
    }
}


