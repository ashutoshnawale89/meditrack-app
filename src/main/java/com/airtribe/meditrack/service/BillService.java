package com.airtribe.meditrack.service;

import com.airtribe.meditrack.constants.BillStatus;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.utils.Validator;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BillService {

    private final List<Bill> bills = new ArrayList<>();

    public void createBill(Bill bill) {
        Validator.validateBill(bill);
        bills.add(bill);
    }

    public Optional<Bill> findById(long billId) {
        return bills.stream()
                .filter(b -> b.getId() == billId)
                .findFirst();
    }

    public List<Bill> getAllBills() {
        return new ArrayList<>(bills);
    }

    public void payBill(long billId) {
        Bill bill = findById(billId)
                .orElseThrow(() ->
                        new InvalidDataException("Bill not found with ID: " + billId));

        bill.pay();
    }

    public void cancelPayment(long billId) {
        Bill bill = findById(billId)
                .orElseThrow(() ->
                        new InvalidDataException("Bill not found with ID: " + billId));

        bill.cancelPayment();
    }

    public boolean isBillPaid(long billId) {
        return findById(billId)
                .map(Bill::isPaid)
                .orElse(false);
    }

    // Advanced Java 8: Get bills by status using Stream
    public List<Bill> getBillsByStatus(BillStatus status) {
        return bills.stream()
                .filter(bill -> bill.getStatus() == status)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Calculate total amount by status
    public double getTotalAmountByStatus(BillStatus status) {
        return bills.stream()
                .filter(bill -> bill.getStatus() == status)
                .mapToDouble(Bill::getAmount)
                .sum();
    }

    // Advanced Java 8: Get bill statistics
    public Map<String, Double> getBillStatistics() {
        return Map.of(
                "total", bills.stream().mapToDouble(Bill::getAmount).sum(),
                "paid", getTotalAmountByStatus(BillStatus.PAID),
                "pending", getTotalAmountByStatus(BillStatus.PENDING),
                "average", bills.stream().mapToDouble(Bill::getAmount).average().orElse(0.0)
        );
    }

    // Advanced Java 8: Find bills by predicate
    public List<Bill> findBillsByPredicate(Predicate<Bill> predicate) {
        return bills.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Group bills by status
    public Map<BillStatus, List<Bill>> groupBillsByStatus() {
        return bills.stream()
                .collect(Collectors.groupingBy(Bill::getStatus));
    }

    // Advanced Java 8: Get unpaid bills sorted by amount descending
    public List<Bill> getUnpaidBillsSortedByAmount() {
        return bills.stream()
                .filter(bill -> bill.getStatus() != BillStatus.PAID)
                .sorted(Comparator.comparingDouble(Bill::getAmount).reversed())
                .collect(Collectors.toList());
    }
}
