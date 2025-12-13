package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.utils.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
}
