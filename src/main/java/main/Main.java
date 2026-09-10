package main;

import expense.ExpensesService;

public class Main {
    public static ExpensesService expensesService = new ExpensesService();

    public static void main(String[] args) {

        //TODO
        // add categories and filter by those categories
        // add budget feature
        // export expenses to a CSV file

        // <test-command>
        // expense-tracker add --description "Burger" --amount 10

        expensesService.executeTracker();
    }
}
