package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.BillStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillSummary {

    private List<Bill> bills = new ArrayList<>();
    private double totalAmount;
    private double paidAmount;
    private double pendingAmount;
    private LocalDateTime generatedAt;

    public BillSummary() {
    }

    public void addBill(Bill bill) {
        bills.add(bill);
        totalAmount += bill.getAmount();
        if (bill.getStatus() == BillStatus.PAID) {
            paidAmount += bill.getAmount();
        } else if (bill.getStatus() == BillStatus.PENDING) {
            pendingAmount += bill.getAmount();
        }
    }

    public List<Bill> getBills() { return bills; }
    public double getTotalAmount() { return totalAmount; }
    public double getPaidAmount() { return paidAmount; }
    public double getPendingAmount() { return pendingAmount; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
}
