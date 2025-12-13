package com.airtribe.meditrack;

import com.airtribe.meditrack.constants.*;
import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.*;
import com.airtribe.meditrack.service.*;
import com.airtribe.meditrack.utils.Validator;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private static final DoctorService doctorService = new DoctorService();
    private static final PatientService patientService = new PatientService();
    private static final AppointmentService appointmentService = new AppointmentService();
    private static final BillService billService = new BillService();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

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
                    case 0 -> {
                        running = false;
                        System.out.println("Exiting MediTrack. Goodbye!");
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (RuntimeException ex) {
                System.out.println("❌ Error: " + ex.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== MediTrack Menu =====");
        System.out.println("1. Add Doctor");
        System.out.println("2. Add Patient");
        System.out.println("3. Book Appointment");
        System.out.println("4. Cancel Appointment");
        System.out.println("5. Generate Bill");
        System.out.println("6. Pay Bill");
        System.out.println("7. List All Appointments");
        System.out.println("8. Download Summury Bills");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addDoctor() {

    }

    private static void addPatient() {

    }

    private static void bookAppointment() {

    }

    private static void downloadSummuryBills() {

    }

    private static void cancelAppointment() {
        System.out.print("Appointment ID: ");
        long id = scanner.nextLong();
        appointmentService.cancelAppointment(id);
        System.out.println("✅ Appointment canceled");
    }

    private static void generateBill() {
        System.out.print("Bill ID: ");
        long billId = scanner.nextLong();

        System.out.print("Appointment ID: ");
        long appointmentId = scanner.nextLong();

        Appointment appointment = appointmentService.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException());

        Bill bill = new Bill(billId, appointment, 500.0);
        billService.createBill(bill);

        System.out.println("✅ Bill generated");
    }

    private static void payBill() {
        System.out.print("Bill ID: ");
        long billId = scanner.nextLong();
        billService.payBill(billId);
        System.out.println("✅ Bill paid");
    }

    private static void listAppointments() {
        appointmentService.getAllAppointments()
                .forEach(System.out::println);
    }
}

