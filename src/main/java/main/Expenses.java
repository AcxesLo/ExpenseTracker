package main;

import java.util.concurrent.atomic.AtomicInteger;

public class Expenses {
    private String description;
    private int amount;
    private static int sumAmount;
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int expenseID;

    public Expenses(String description, int amount) {
        this.description = description;
        this.amount = amount;
        sumAmount += amount;
        expenseID = count.incrementAndGet();
    }

    @Override
    public String toString() {
        return "ID:" + expenseID + " Description:\"" + description + "\" Amount:$" +  amount;
    }

    public static int getSumAmount() {
        return sumAmount;
    }
}
