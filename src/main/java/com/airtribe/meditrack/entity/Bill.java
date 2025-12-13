package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.BillStatus;
import com.airtribe.meditrack.interfaces.Payable;

import java.time.LocalDateTime;

public class Bill implements Payable {
    private long id;
    private Appointment appointment;
    private double amount;
    private BillStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime paymentDate;
    private String notes;

    // Constructor
    public Bill() {
        this.createdAt = LocalDateTime.now();
        this.status = BillStatus.PENDING;
    }

    public Bill(long id, Appointment appointment, double amount) {
        this.id = id;
        this.appointment = appointment;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
        this.status = BillStatus.PENDING;
    }

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public BillStatus getStatus() { return status; }
    public void setStatus(BillStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // ---------------- Payable methods ----------------

    @Override
    public void pay() {
        if (status == BillStatus.PENDING) {
            this.status = BillStatus.PAID;
            this.paymentDate = LocalDateTime.now();
            System.out.println("Bill #" + id + " has been paid successfully.");
        } else {
            System.out.println("Bill #" + id + " is already " + status);
        }
    }

    @Override
    public boolean isPaid() {
        return status == BillStatus.PAID;
    }

    @Override
    public void cancelPayment() {
        if (status == BillStatus.PAID) {
            this.status = BillStatus.PENDING;
            this.paymentDate = null;
            System.out.println("Payment for Bill #" + id + " has been canceled.");
        } else {
            System.out.println("Cannot cancel payment. Bill #" + id + " is " + status);
        }
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", appointmentId=" + (appointment != null ? appointment.getId() : "N/A") +
                ", amount=" + amount +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", paymentDate=" + paymentDate +
                ", notes='" + notes + '\'' +
                '}';
    }
}

