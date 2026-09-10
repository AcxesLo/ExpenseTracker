package main;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Expenses {
    private String description;
    private int amount;
    private static int sumAmount;
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int expenseID;
    private final LocalDate localDate;

    public Expenses(String description, int amount) {
        this.description = description;
        this.amount = amount;
        this.localDate = LocalDate.now();
        expenseID = count.incrementAndGet();
        sumAmount += amount;
    }

    @Override
    public String toString() {
        return "ID:" + expenseID
                + " Date:" + localDate
                + " Description:" + description
                + " Amount:$" + amount;
    }

    public String getDescription() {
        return this.description;
    }

    public int getAmount() {
        return this.amount;
    }

    public LocalDate getLocalDate() {
        return this.localDate;
    }

    public int getExpenseID() {
        return this.expenseID;
    }

    public static int getSumAmount() {
        return sumAmount;
    }

    public static void subtractFromSum(int amount) {
        sumAmount -= amount;
    }
}
