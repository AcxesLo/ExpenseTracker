package csv;

import expense.Expenses;
import expense.ExpensesLogic;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {

    public void writeCSVFile() {
//        ExpensesLogic expensesLogic = new ExpensesLogic();
//        String user = System.getProperty("user.name");
//        "C:\\Users\\" + user + "\\Desktop\\expenses.csv"
        File file = new File("expenses.csv");

        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File "
                        + "'"
                        + file.getName()
                        + "'"
                        + " already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (CSVPrinter csvPrinter = new CSVPrinter(
                new FileWriter(file, true),
                CSVFormat.DEFAULT.builder()
                        .setHeader("id", "Date", "Description", "Amount")
                        .build());) {

            for (Expenses expenses : ExpensesLogic.expensesList) {
                csvPrinter.printRecord(
                        expenses.getExpenseID(),
                        expenses.getLocalDate(),
                        expenses.getDescription(),
                        expenses.getAmount());
            }

            System.out.println("Data has been written.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}