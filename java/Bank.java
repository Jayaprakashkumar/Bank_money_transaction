//package com.course.example.models;

import java.util.concurrent.atomic.AtomicLong;

public class Bank {
    private final String name;
    private final AtomicLong financeMargin;
    private final AtomicLong lentAmount;

    public Bank(String name, String financeMargin) {
        this.name = name;
        this.lentAmount = new AtomicLong(0);
        if (financeMargin != null && !financeMargin.trim().equals(""))
            this.financeMargin = new AtomicLong(Long.parseLong(financeMargin));
        else
            this.financeMargin = new AtomicLong(0);
    }

    public String getName() {
        return name;
    }

    public AtomicLong getFinanceMargin() {
        return financeMargin;
    }

    public AtomicLong getLentAmount() {
        return lentAmount;
    }

    @Override
    public String toString() {
        return name + ": " + financeMargin.get();
    }
    
   
    public String displayString() {

        if (financeMargin.get() >= lentAmount.get())
            return name + " has " + (financeMargin.get() - lentAmount.get()) + " dollar(s) remaining";

        return name + " bank has bankrupted";
    }
}
