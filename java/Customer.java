//package com.course.example.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Manivannan on 6/18/19.
 */
public class Customer {
    private final String name;
    private final AtomicLong loanObjective;

    private final CopyOnWriteArrayList<String> rejectedBanks = new CopyOnWriteArrayList<>();

    public Customer(String name, String loanObjective) {
        this.name = name;
        if (loanObjective != null && !loanObjective.trim().equals(""))
            this.loanObjective = new AtomicLong(Long.parseLong(loanObjective));
        else
            this.loanObjective = new AtomicLong(0);
    }

    private final AtomicLong loanAmount = new AtomicLong(0);

    public String getName() {
        return name;
    }

    public AtomicLong getLoanObjective() {
        return loanObjective;
    }

    public AtomicLong getLoanAmount() {
        return loanAmount;
    }

    public boolean isLoanObjectiveMet() {
        return this.loanObjective.get() <= this.loanAmount.get();
    }

    public CopyOnWriteArrayList<String> getRejectedBanks() {
        return rejectedBanks;
    }

    @Override
    public String toString() {
        return name + ": " + loanObjective.get();
    }

    public String displayString() {
        if (loanAmount.get()  >= loanObjective.get())
            return name + " has reached the objective of " + loanAmount.get() + " dollar(s). Woo Hoo!";
        return name + " was only able to borrow " + loanAmount.get() + " dollar(s). Boo Hoo!";
    }
}
