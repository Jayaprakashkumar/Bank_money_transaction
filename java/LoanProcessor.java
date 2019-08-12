//package com.course.example.worker;
//
//import com.course.example.models.Bank;
//import com.course.example.models.Customer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class LoanProcessor implements Runnable {
    final Customer customer;
    final Bank bank;
    final Integer requestedAmount;

    public LoanProcessor(final Customer customer, final Bank bank, final Integer requestedAmount) {
        this.customer = customer;
        this.bank = bank;
        this.requestedAmount = requestedAmount;
    }

    @Override
    public void run() {
        if(requestedAmount < 0 || requestedAmount > 50)
            return;

        final Long newLoanAmount = customer.getLoanAmount().get() + requestedAmount;
        if (newLoanAmount > customer.getLoanObjective().get()) {
            return;
        }
        if (bank.getLentAmount().get() + requestedAmount > bank.getFinanceMargin().get()) {
            customer.getRejectedBanks().add(bank.getName());
            System.out.println(bank.getName() +" denies a loan of "+ requestedAmount +" dollars from "+ customer.getName());
            return;
        }

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bank.getLentAmount().addAndGet(requestedAmount);
        customer.getLoanAmount().addAndGet(requestedAmount);
        System.out.println(bank.getName() +" approves a loan of "+requestedAmount +" dollars from "+customer.getName());
    }
}
