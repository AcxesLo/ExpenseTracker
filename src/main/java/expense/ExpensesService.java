package expense;

import csv.CSVWriter;

import java.util.Scanner;

public class ExpensesService {
    private final ExpensesLogic expensesLogic = new ExpensesLogic();
    private final CSVWriter csvWriter = new CSVWriter();

    public void executeTracker() {
        while (true) {
            System.out.print(">");

            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            String[] splitInput = userInput.split("\\s+");

            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }

            if (userInput.equalsIgnoreCase("write csv")) {
                csvWriter.writeCSVFile();
            }

            if (splitInput[0].equalsIgnoreCase("expense-tracker")
                    && splitInput[splitInput.length - 1].equalsIgnoreCase("--help")) {
                expensesLogic.printCommands();
            }

            if (splitInput[0].equalsIgnoreCase("expense-tracker")
                    && splitInput[1].equalsIgnoreCase("add")
                    && splitInput[2].equalsIgnoreCase("--description")
                    && splitInput[splitInput.length - 2].equalsIgnoreCase("--amount")
                    && ExpensesLogic.isNumeric(splitInput[splitInput.length - 1])) {

                expensesLogic.addFunction(userInput, splitInput);
            }

            if (splitInput[0].equalsIgnoreCase("expense-tracker")
                    && splitInput[1].equalsIgnoreCase("delete")
                    && splitInput[splitInput.length - 2].equalsIgnoreCase("--id")
                    && ExpensesLogic.isNumeric(splitInput[splitInput.length - 1])) {

                expensesLogic.deleteFunction(splitInput);
            }

            if (splitInput[0].equalsIgnoreCase("expense-tracker") &&
                    splitInput[1].equalsIgnoreCase("list")) {

                expensesLogic.listFunction();
            }

            if (splitInput[0].equalsIgnoreCase("expense-tracker")
                    && splitInput[splitInput.length - 1].equalsIgnoreCase("summary")) {

                expensesLogic.summaryFunction();
            }

            if (splitInput[0].equalsIgnoreCase("expense-tracker")
                    && splitInput[1].equalsIgnoreCase("summary")
                    && splitInput[splitInput.length - 2].equalsIgnoreCase("--month")
                    && ExpensesLogic.isNumeric(splitInput[splitInput.length - 1])) {

                expensesLogic.summaryByMonthFunction(splitInput);
            }
        }
    }
}
