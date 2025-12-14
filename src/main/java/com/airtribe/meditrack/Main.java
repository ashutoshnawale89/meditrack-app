package com.airtribe.meditrack;

import com.airtribe.meditrack.constants.*;
import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.*;
import com.airtribe.meditrack.service.*;

import java.io.FileOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Main entry point for MediTrack Healthcare Management System.
 * 
 * <p>MediTrack is a comprehensive healthcare management application that provides
 * functionality for managing doctors, patients, appointments, and billing operations.
 * The system leverages advanced Java 8 features including lambda expressions, Stream API,
 * and functional programming patterns.</p>
 * 
 * <h2>Features:</h2>
 * <ul>
 *   <li>Doctor management with specialization and availability tracking</li>
 *   <li>Patient registration and record management</li>
 *   <li>Appointment scheduling and cancellation</li>
 *   <li>Bill generation, payment processing, and reporting</li>
 *   <li>Excel-based bill summary export functionality</li>
 * </ul>
 * 
 * <h2>Usage:</h2>
 * <pre>
 * Interactive Mode:
 *   java com.airtribe.meditrack.Main
 * 
 * Command-Line Mode:
 *   java com.airtribe.meditrack.Main --help
 *   java com.airtribe.meditrack.Main --demo
 * </pre>
 * 
 * @author MediTrack Development Team
 * @version 1.0
 * @since 2025-12-14
 * @see DoctorService
 * @see PatientService
 * @see AppointmentService
 * @see BillService
 */
public class Main {

    /** Service for managing doctor operations */
    private static final DoctorService doctorService = new DoctorService();
    
    /** Service for managing patient operations */
    private static final PatientService patientService = new PatientService();
    
    /** Service for managing appointment operations */
    private static final AppointmentService appointmentService = new AppointmentService();
    
    /** Service for managing billing operations */
    private static final BillService billService = new BillService();

    /** Scanner for reading user input from console */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Main entry point for the MediTrack application.
     * 
     * <p>Supports both interactive menu mode and command-line arguments:</p>
     * <ul>
     *   <li>No arguments: Launches interactive menu</li>
     *   <li>--help, -h: Displays help information</li>
     *   <li>--demo: Runs demonstration with sample data</li>
     *   <li>--version, -v: Shows version information</li>
     * </ul>
     * 
     * @param args Command line arguments for controlling application behavior
     */
    public static void main(String[] args) {
        // Load default sample data at startup
        initializeDefaultData();
        
        // Handle command-line arguments
        if (args.length > 0) {
            handleCommandLineArgs(args);
            return;
        }

        printWelcomeBanner();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            try {
                switch (choice) {
                    case 1 -> addDoctor();
                    case 2 -> addPatient();
                    case 3 -> bookAppointment();
                    case 4 -> cancelAppointment();
                    case 5 -> generateBill();
                    case 6 -> payBill();
                    case 7 -> listAppointments();
                    case 8 -> downloadSummuryBills();
                    case 9 -> getAllDoctorsData();
                    case 10 -> getAllPatientsData();
                    case 0 -> {
                        running = false;
                        System.out.println("\n👋 Exiting MediTrack. Thank you for using our system!");
                    }
                    default -> System.out.println("❌ Invalid option. Please try again.");
                }
            } catch (RuntimeException ex) {
                System.out.println("❌ Error: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("❌ Unexpected error: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        scanner.close();
    }

    /**
     * Handles command-line arguments for non-interactive mode.
     * 
     * @param args Array of command-line arguments
     */
    private static void handleCommandLineArgs(String[] args) {
        String command = args[0].toLowerCase();
        
        switch (command) {
            case "--help", "-h" -> printHelp();
            case "--version", "-v" -> printVersion();
            case "--demo" -> runDemo();
            default -> {
                System.out.println("Unknown command: " + command);
                System.out.println("Use --help for usage information");
            }
        }
    }

    /**
     * Prints the welcome banner with application information.
     */
    private static void printWelcomeBanner() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  MediTrack Healthcare Management System v1.0");
        System.out.println("  Advanced Java 8 Medical Practice Solution");
        System.out.println("=".repeat(60) + "\n");
    }

    /**
     * Displays the main menu options to the user.
     */
    private static void printMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("  MediTrack Main Menu");
        System.out.println("=".repeat(40));
        System.out.println("1. 🩺 Add Doctor");
        System.out.println("2. 👤 Add Patient");
        System.out.println("3. 📅 Book Appointment");
        System.out.println("4. ❌ Cancel Appointment");
        System.out.println("5. 💰 Generate Bill");
        System.out.println("6. 💳 Pay Bill");
        System.out.println("7. 📋 List All Appointments");
        System.out.println("8. 📊 Download Bill Summary (Excel)");
        System.out.println("9. 🩺 List All Doctors");
        System.out.println("10. 👤 List All Patients");
        System.out.println("0. 🚪 Exit");
        System.out.println("=".repeat(40));
        System.out.print("Choose an option: ");
    }

    /**
     * Adds a new doctor to the system.
     * 
     * <p>Collects doctor information including:
     * <ul>
     *   <li>Unique ID</li>
     *   <li>Name</li>
     *   <li>Years of experience</li>
     *   <li>Medical specialization</li>
     *   <li>Available days</li>
     *   <li>Working hours (from/to)</li>
     * </ul>
     * </p>
     * 
     * <p>Uses Java 8 Stream API for specialization validation and
     * DayOfWeek parsing with lambda expressions.</p>
     * 
     * @throws InvalidDataException if doctor data validation fails
     */
    private static void addDoctor() {
        System.out.println("\n--- Add New Doctor ---");
        System.out.print("Doctor ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); 

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Experience (years): ");
        int experience = scanner.nextInt();
        scanner.nextLine(); 

        System.out.println("\nAvailable Specializations:");
        Arrays.stream(Specialization.values())
                .forEach(spec -> System.out.println("  - " + spec));
        
        System.out.print("\nSpecialization: ");
        String specInput = scanner.nextLine();
        Specialization specialization = Arrays.stream(Specialization.values())
                .filter(spec -> spec.name().equalsIgnoreCase(specInput.replace(" ", "_")))
                .findFirst()
                .orElseThrow(() -> new InvalidDataException("Invalid specialization: " + specInput));

        System.out.print("Available Days (comma-separated, e.g., MONDAY,WEDNESDAY,FRIDAY): ");
        String daysInput = scanner.nextLine();
        Set<DayOfWeek> availableDays = Set.of(
                Arrays.stream(daysInput.split(","))
                        .map(String::trim)
                        .map(String::toUpperCase)
                        .map(DayOfWeek::valueOf)
                        .toArray(DayOfWeek[]::new)
        );

        System.out.print("Available From (HH:MM, e.g., 09:00): ");
        String fromTime = scanner.nextLine();
        java.time.LocalTime availableFrom = java.time.LocalTime.parse(fromTime);

        System.out.print("Available To (HH:MM, e.g., 17:00): ");
        String toTime = scanner.nextLine();
        java.time.LocalTime availableTo = java.time.LocalTime.parse(toTime);

        Doctor doctor = new Doctor(id, name, experience, specialization, availableDays, 
                LocalDateTime.now(), availableFrom, availableTo);
        doctorService.addDoctor(doctor);
        System.out.println("\n✅ Doctor added successfully!");
    }

    /**
     * Adds a new patient to the system.
     * 
     * <p>Collects patient and personal information including:
     * <ul>
     *   <li>Patient ID</li>
     *   <li>Person details (ID, name, age, mobile)</li>
     *   <li>Medical record number</li>
     * </ul>
     * </p>
     * 
     * @throws InvalidDataException if patient data validation fails
     */
    private static void addPatient() {
        System.out.println("\n--- Add New Patient ---");
        System.out.print("Patient ID: ");
        long patientId = scanner.nextLong();
        scanner.nextLine(); 

        System.out.print("Person ID: ");
        long personId = scanner.nextLong();
        scanner.nextLine(); 

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Mobile Number (10 digits): ");
        String mobileNo = scanner.nextLine();

        System.out.print("Medical Record Number: ");
        String medicalRecordNumber = scanner.nextLine();

        Person person = new Person(personId, name, age, mobileNo);
        Patient patient = new Patient(patientId, person, medicalRecordNumber);
        patientService.addPatient(patient);
        System.out.println("\n✅ Patient registered successfully!");
    }

    /**
     * Books a new appointment for a patient.
     * 
     * <p>Creates an appointment with:
     * <ul>
     *   <li>Unique appointment ID</li>
     *   <li>Associated patient (must exist)</li>
     *   <li>Date and time</li>
     *   <li>Optional notes</li>
     * </ul>
     * </p>
     * 
     * @throws InvalidDataException if patient not found or invalid date
     */
    private static void bookAppointment() {
        System.out.println("\n--- Book New Appointment ---");
        System.out.print("Appointment ID: ");
        long appointmentId = scanner.nextLong();
        scanner.nextLine(); 

        System.out.print("Patient ID: ");
        long patientId = scanner.nextLong();
        scanner.nextLine(); 

        Patient patient = patientService.findById(patientId)
                .orElseThrow(() -> new InvalidDataException("Patient not found with ID: " + patientId));

        System.out.print("Appointment Date & Time (yyyy-MM-ddTHH:mm, e.g., 2024-12-25T10:30): ");
        String dateTimeInput = scanner.nextLine();
        LocalDateTime appointmentDateTime = LocalDateTime.parse(dateTimeInput);

        Appointment appointment = new Appointment(appointmentId, patient, appointmentDateTime);
        
        System.out.print("Notes (optional, press Enter to skip): ");
        String notes = scanner.nextLine();
        if (!notes.isEmpty()) {
            appointment.setNotes(notes);
        }

        appointmentService.bookAppointment(appointment);
        System.out.println("\n✅ Appointment booked successfully!");
    }

    /**
     * Downloads and generates an Excel bill summary report.
     * 
     * <p>Features:</p>
     * <ul>
     *   <li>Aggregate financial statistics</li>
     *   <li>Detailed bill listing with patient information</li>
     *   <li>Professional Excel formatting</li>
     *   <li>Timestamped filename</li>
     *   <li>Auto-sized columns</li>
     * </ul>
     * 
     * <p>Uses Apache POI for Excel generation and Java 8 IntStream
     * for efficient column operations.</p>
     * 
     * @see BillSummary
     * @see org.apache.poi.ss.usermodel.Workbook
     */
    private static void downloadSummuryBills() {
        System.out.println("\n--- Generating Bill Summary Report ---");
        
        BillSummary billSummary = new BillSummary();
        
        billService.getAllBills()
                .forEach(billSummary::addBill);

        System.out.println("\n" + "=".repeat(40));
        System.out.println("  Bill Summary");
        System.out.println("=".repeat(40));
        System.out.println("Total Bills:     " + billSummary.getBills().size());
        System.out.println("Total Amount:    $" + String.format("%.2f", billSummary.getTotalAmount()));
        System.out.println("Paid Amount:     $" + String.format("%.2f", billSummary.getPaidAmount()));
        System.out.println("Pending Amount:  $" + String.format("%.2f", billSummary.getPendingAmount()));
        System.out.println("=".repeat(40) + "\n");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Bill Summary");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            int rowNum = 0;
            Row summaryTitleRow = sheet.createRow(rowNum++);
            Cell summaryTitleCell = summaryTitleRow.createCell(0);
            summaryTitleCell.setCellValue("BILL SUMMARY REPORT");
            summaryTitleCell.setCellStyle(headerStyle);

            rowNum++;

            Row totalBillsRow = sheet.createRow(rowNum++);
            totalBillsRow.createCell(0).setCellValue("Total Bills:");
            totalBillsRow.createCell(1).setCellValue(billSummary.getBills().size());

            Row totalAmountRow = sheet.createRow(rowNum++);
            totalAmountRow.createCell(0).setCellValue("Total Amount:");
            totalAmountRow.createCell(1).setCellValue("$" + billSummary.getTotalAmount());

            Row paidAmountRow = sheet.createRow(rowNum++);
            paidAmountRow.createCell(0).setCellValue("Paid Amount:");
            paidAmountRow.createCell(1).setCellValue("$" + billSummary.getPaidAmount());

            Row pendingAmountRow = sheet.createRow(rowNum++);
            pendingAmountRow.createCell(0).setCellValue("Pending Amount:");
            pendingAmountRow.createCell(1).setCellValue("$" + billSummary.getPendingAmount());

            rowNum += 2; 

            Row headerRow = sheet.createRow(rowNum++);
            String[] columns = {"Bill ID", "Appointment ID", "Patient Name", "Amount", "Status", "Created Date"};
            IntStream.range(0, columns.length)
                    .forEach(i -> {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(columns[i]);
                        cell.setCellStyle(headerStyle);
                    });

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (Bill bill : billSummary.getBills()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(bill.getId());
                row.createCell(1).setCellValue(bill.getAppointment().getId());
                row.createCell(2).setCellValue(bill.getAppointment().getPatient().getPerson().getName());
                row.createCell(3).setCellValue("$" + bill.getAmount());
                row.createCell(4).setCellValue(bill.getStatus().toString());
                row.createCell(5).setCellValue(bill.getCreatedAt() != null ? 
                    bill.getCreatedAt().format(formatter) : "N/A");
            }

            // Advanced Java 8: Auto-size columns using IntStream and method reference
            IntStream.range(0, columns.length)
                    .forEach(sheet::autoSizeColumn);

            String fileName = "BillSummary_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
                System.out.println("✅ Bill summary exported successfully!");
                System.out.println("📄 File saved as: " + fileName);
            }

        } catch (Exception e) {
            System.out.println("❌ Error generating Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Cancels an existing appointment.
     * 
     * @throws AppointmentNotFoundException if appointment ID not found
     */
    private static void cancelAppointment() {
        System.out.println("\n--- Cancel Appointment ---");
        System.out.print("Appointment ID: ");
        long id = scanner.nextLong();
        appointmentService.cancelAppointment(id);
        System.out.println("\n✅ Appointment canceled successfully");
    }

    /**
     * Generates a bill for a completed appointment.
     * 
     * @throws AppointmentNotFoundException if appointment ID not found
     */
    private static void generateBill() {
        System.out.println("\n--- Generate Bill ---");
        System.out.print("Bill ID: ");
        long billId = scanner.nextLong();

        System.out.print("Appointment ID: ");
        long appointmentId = scanner.nextLong();

        Appointment appointment = appointmentService.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

        Bill bill = new Bill(billId, appointment, 500.0);
        billService.createBill(bill);

        System.out.println("\n✅ Bill generated successfully");
        System.out.println("💰 Amount: $500.00");
    }

    /**
     * Processes payment for a pending bill.
     * 
     * @throws InvalidDataException if bill ID not found
     */
    private static void payBill() {
        System.out.println("\n--- Pay Bill ---");
        System.out.print("Bill ID: ");
        long billId = scanner.nextLong();
        billService.payBill(billId);
        System.out.println("\n✅ Payment processed successfully");
    }

    /**
     * Lists all appointments in the system.
     * Uses Java 8 method reference for efficient output.
     */
    private static void listAppointments() {
        System.out.println("\n--- All Appointments ---");
        List<Appointment> appointments = appointmentService.getAllAppointments();
        
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.println("Total: " + appointments.size() + " appointment(s)\n");
            System.out.println(String.format("%-5s %-20s %-25s %-15s", 
                "ID", "Patient", "Date/Time", "Status"));
            System.out.println("-".repeat(70));
            
            appointments.forEach(apt -> {
                String patientName = apt.getPatient() != null && apt.getPatient().getPerson() != null 
                    ? apt.getPatient().getPerson().getName() : "Unknown";
                String dateTime = apt.getAppointmentDateTime() != null 
                    ? apt.getAppointmentDateTime().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")) 
                    : "Not scheduled";
                System.out.println(String.format("%-5d %-20s %-25s %-15s", 
                    apt.getId(), 
                    patientName, 
                    dateTime, 
                    apt.getStatus()));
            });
        }
    }

    private static void getAllDoctorsData() {
        System.out.println("\n--- All Doctors ---");
        List<Doctor> doctors = doctorService.getAllDoctors();
        
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
        } else {
            System.out.println("Total: " + doctors.size() + " doctor(s)\n");
            System.out.println(String.format("%-5s %-25s %-15s %-12s %-20s", 
                "ID", "Name", "Specialization", "Experience", "Available Days"));
            System.out.println("-".repeat(80));
            
            doctors.forEach(doc -> {
                String availableDays = doc.getAvailableDays() != null 
                    ? doc.getAvailableDays().stream()
                        .map(day -> day.toString().substring(0, 3))
                        .collect(java.util.stream.Collectors.joining(", "))
                    : "Not set";
                System.out.println(String.format("%-5d %-25s %-15s %-12s %-20s", 
                    doc.getId(), 
                    doc.getName(), 
                    doc.getSpecialization(), 
                    doc.getExperience() + " years",
                    availableDays));
            });
        }
    }

    private static void getAllPatientsData() {
        System.out.println("\n--- All Patients ---");
        List<Patient> patients = patientService.getAllPatients();
        
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
        } else {
            System.out.println("Total: " + patients.size() + " patient(s)\n");
            System.out.println(String.format("%-5s %-20s %-6s %-15s %-25s %-10s", 
                "ID", "Name", "Age", "Phone", "Assigned Doctor", "Status"));
            System.out.println("-".repeat(85));
            
            patients.forEach(patient -> {
                String name = patient.getPerson() != null ? patient.getPerson().getName() : "Unknown";
                int age = patient.getPerson() != null ? patient.getPerson().getAge() : 0;
                String phone = patient.getPerson() != null ? patient.getPerson().getMobileNo() : "N/A";
                String doctor = patient.getAssignedDoctors() != null 
                    ? patient.getAssignedDoctors().getName() 
                    : "Not assigned";
                String status = patient.isActive() ? "Active" : "Inactive";
                
                System.out.println(String.format("%-5d %-20s %-6d %-15s %-25s %-10s", 
                    patient.getId(), 
                    name, 
                    age, 
                    phone, 
                    doctor,
                    status));
            });
        }
    }

    /**
     * Prints command-line help information.
     */
    private static void printHelp() {
        System.out.println("\nMediTrack Healthcare Management System - Help");
        System.out.println("=".repeat(60));
        System.out.println("Usage: java com.airtribe.meditrack.Main [options]");
        System.out.println("\nOptions:");
        System.out.println("  (no args)         Launch interactive menu mode");
        System.out.println("  --help, -h        Show this help message");
        System.out.println("  --version, -v     Display version information");
        System.out.println("  --demo            Run demonstration with sample data");
        System.out.println("\nFeatures:");
        System.out.println("  • Doctor management with specialization tracking");
        System.out.println("  • Patient registration and medical records");
        System.out.println("  • Appointment scheduling and management");
        System.out.println("  • Billing and payment processing");
        System.out.println("  • Excel report generation");
        System.out.println("\nFor testing, use:");
        System.out.println("  java com.airtribe.meditrack.test.TestRunner");
        System.out.println("=".repeat(60));
    }

    /**
     * Prints version information.
     */
    private static void printVersion() {
        System.out.println("\nMediTrack Healthcare Management System");
        System.out.println("Version: 1.0.0");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Build Date: 2025-12-14");
        System.out.println("© 2025 MediTrack Development Team");
    }

    /**
     * Runs a demonstration with sample data.
     * Useful for testing and showcasing features.
     */
    private static void runDemo() {
        System.out.println("\n🎬 Running MediTrack Demo...\n");
        
        // Add sample doctor
        Doctor doctor = new Doctor(1L, "Dr. Demo Smith", 10, Specialization.CARDIOLOGY,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                LocalDateTime.now(), java.time.LocalTime.of(9, 0), java.time.LocalTime.of(17, 0));
        doctorService.addDoctor(doctor);
        System.out.println("✅ Added sample doctor: Dr. Demo Smith (Cardiology)");
        
        // Add sample patient
        Person person = new Person(1L, "Demo Patient", 35, "1234567890");
        Patient patient = new Patient(1L, person, "MRN001");
        patientService.addPatient(patient);
        System.out.println("✅ Registered sample patient: Demo Patient");
        
        // Book appointment
        Appointment appointment = new Appointment(1L, patient, LocalDateTime.now().plusDays(1));
        appointmentService.bookAppointment(appointment);
        System.out.println("✅ Booked sample appointment for tomorrow");
        
        // Generate bill
        Bill bill = new Bill(1L, appointment, 500.0);
        billService.createBill(bill);
        System.out.println("✅ Generated sample bill: $500.00");
        
        System.out.println("\n🎉 Demo completed! Sample data has been loaded.");
        System.out.println("   You can now explore the system features.");
    }

    /**
     * Initializes default sample data for the application.
     * This method is automatically called at startup to populate the system
     * with dummy doctors, patients, and appointments for demonstration purposes.
     * 
     * <p>Default data includes:</p>
     * <ul>
     *   <li>5 Doctors across different specializations</li>
     *   <li>5 Patients with varied demographics</li>
     *   <li>3 Scheduled appointments</li>
     *   <li>2 Bills (1 paid, 1 pending)</li>
     * </ul>
     */
    private static void initializeDefaultData() {
        System.out.println("\n🔄 Initializing default data...\n");
        
        // Initialize Doctors
        Doctor doctor1 = new Doctor(1L, "Dr. Sarah Johnson", 15, Specialization.CARDIOLOGY,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                LocalDateTime.now(), java.time.LocalTime.of(9, 0), java.time.LocalTime.of(17, 0));
        
        Doctor doctor2 = new Doctor(2L, "Dr. Michael Chen", 12, Specialization.NEUROLOGY,
                Set.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
                LocalDateTime.now(), java.time.LocalTime.of(10, 0), java.time.LocalTime.of(18, 0));
        
        Doctor doctor3 = new Doctor(3L, "Dr. Emily Rodriguez", 8, Specialization.PEDIATRICS,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalDateTime.now(), java.time.LocalTime.of(8, 0), java.time.LocalTime.of(16, 0));
        
        Doctor doctor4 = new Doctor(4L, "Dr. James Wilson", 20, Specialization.ORTHOPEDICS,
                Set.of(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                LocalDateTime.now(), java.time.LocalTime.of(9, 30), java.time.LocalTime.of(17, 30));
        
        Doctor doctor5 = new Doctor(5L, "Dr. Priya Sharma", 10, Specialization.DERMATOLOGY,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalDateTime.now(), java.time.LocalTime.of(11, 0), java.time.LocalTime.of(19, 0));
        
        doctorService.addDoctor(doctor1);
        doctorService.addDoctor(doctor2);
        doctorService.addDoctor(doctor3);
        doctorService.addDoctor(doctor4);
        doctorService.addDoctor(doctor5);
        System.out.println("✅ Loaded 5 doctors");
        
        // Initialize Patients
        Person person1 = new Person(1L, "John Smith", 45, "9876543210");
        Patient patient1 = new Patient(1L, person1, "MRN001");
        
        Person person2 = new Person(2L, "Maria Garcia", 32, "9876543211");
        Patient patient2 = new Patient(2L, person2, "MRN002");
        
        Person person3 = new Person(3L, "David Brown", 28, "9876543212");
        Patient patient3 = new Patient(3L, person3, "MRN003");
        
        Person person4 = new Person(4L, "Lisa Anderson", 55, "9876543213");
        Patient patient4 = new Patient(4L, person4, "MRN004");
        
        Person person5 = new Person(5L, "Robert Taylor", 38, "9876543214");
        Patient patient5 = new Patient(5L, person5, "MRN005");
        
        patientService.addPatient(patient1);
        patientService.addPatient(patient2);
        patientService.addPatient(patient3);
        patientService.addPatient(patient4);
        patientService.addPatient(patient5);
        System.out.println("✅ Registered 5 patients");
        
        // Assign doctors to patients
        patientService.assignDoctorToPatient(1L, doctor1);
        patientService.assignDoctorToPatient(2L, doctor2);
        patientService.assignDoctorToPatient(3L, doctor3);
        patientService.assignDoctorToPatient(4L, doctor4);
        patientService.assignDoctorToPatient(5L, doctor5);
        
        // Initialize Appointments
        Appointment apt1 = new Appointment(1L, patient1, LocalDateTime.now().plusDays(2).withHour(10).withMinute(0));
        Appointment apt2 = new Appointment(2L, patient2, LocalDateTime.now().plusDays(3).withHour(14).withMinute(30));
        Appointment apt3 = new Appointment(3L, patient3, LocalDateTime.now().plusDays(5).withHour(11).withMinute(0));
        
        appointmentService.bookAppointment(apt1);
        appointmentService.bookAppointment(apt2);
        appointmentService.bookAppointment(apt3);
        System.out.println("✅ Scheduled 3 appointments");
        
        // Initialize Bills
        Bill bill1 = new Bill(1L, apt1, 1500.0);
        Bill bill2 = new Bill(2L, apt2, 2000.0);
        
        billService.createBill(bill1);
        billService.createBill(bill2);
        billService.payBill(1L); // Mark first bill as paid
        System.out.println("✅ Generated 2 bills (1 paid, 1 pending)");
        
        System.out.println("\n✨ Default data initialized successfully!");
        System.out.println("   - 5 Doctors available");
        System.out.println("   - 5 Patients registered");
        System.out.println("   - 3 Appointments scheduled");
        System.out.println("   - 2 Bills generated\n");
    }
}

