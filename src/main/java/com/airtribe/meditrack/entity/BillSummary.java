package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.BillStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BillSummary {

    private List<Bill> bills = new ArrayList<>();
    private double totalAmount;
    private double paidAmount;
    private double pendingAmount;
    private LocalDateTime generatedAt;

    public BillSummary() {
        this.generatedAt = LocalDateTime.now();
    }

    public void addBill(Bill bill) {
        bills.add(bill);
        recalculateAmounts();
    }

    // Advanced Java 8: Recalculate amounts using Stream API
    private void recalculateAmounts() {
        totalAmount = bills.stream()
                .mapToDouble(Bill::getAmount)
                .sum();
        
        paidAmount = bills.stream()
                .filter(bill -> bill.getStatus() == BillStatus.PAID)
                .mapToDouble(Bill::getAmount)
                .sum();
        
        pendingAmount = bills.stream()
                .filter(bill -> bill.getStatus() == BillStatus.PENDING)
                .mapToDouble(Bill::getAmount)
                .sum();
    }

    // Advanced Java 8: Get bills by status using Stream
    public List<Bill> getBillsByStatus(BillStatus status) {
        return bills.stream()
                .filter(bill -> bill.getStatus() == status)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Get average bill amount
    public double getAverageBillAmount() {
        return bills.stream()
                .mapToDouble(Bill::getAmount)
                .average()
                .orElse(0.0);
    }

    public List<Bill> getBills() { return bills; }
    public double getTotalAmount() { return totalAmount; }
    public double getPaidAmount() { return paidAmount; }
    public double getPendingAmount() { return pendingAmount; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
}
