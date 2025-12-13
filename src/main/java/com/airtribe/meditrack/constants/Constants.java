package com.airtribe.meditrack.constants;

public final class Constants {

    private Constants() {}

    public static final double TAX_RATE = 0.05;

    public static final String BILL_OUTPUT_PATH = "data/bills/";
    public static final String PATIENT_DATA_FILE = "data/patients.json";
    public static final String DOCTOR_DATA_FILE = "data/doctors.json";
    public static final String APPOINTMENT_DATA_FILE = "data/appointments.json";

    public static final int DEFAULT_APPOINTMENT_DURATION_MINUTES = 30;

    public static final String PAYMENT_SUCCESS_MESSAGE = "Payment completed successfully";
    public static final String PAYMENT_FAILED_MESSAGE = "Payment failed. Please try again.";

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

}
