package main;

public class Main {
    public static ExpensesService expensesService = new ExpensesService();

    public static void main(String[] args) {

        //TODO
        // ~

        // test-command
        // expense-tracker add --description "hello world" --amount 100

        expensesService.executeTracker();
    }
}
