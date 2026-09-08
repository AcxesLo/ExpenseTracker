package main;

public class Expenses {
    private String description;
    private String amountString;
    private int amount;
    private static int sumAmount;

    public Expenses(String description, int amount) {
        this.description = description;
        this.amount = amount;
        sumAmount += amount;
    }

    public Expenses(String description, String amountString) {
        this.description = description;
        this.amountString = amountString;
    }

    @Override
    public String toString() {
        return description + amount;
    }

    public static int getSumAmount() {
        return sumAmount;
    }
}
