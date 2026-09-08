package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static String description;
    public static int value;
    public static List<Expenses> expensesList = new ArrayList<>();

    public static void main(String[] args) {

        //TODO
        // - add ID and Date
        // - ID, Date, Description and Amount Strings must match with the actual values space wise at the list command
        // - delete function
        // - summary --month {value}
        // - fix issue with having a too high int value

        Scanner scanner = new Scanner(System.in);

        // expense-tracker add --description "hello world" --value 123123

        while (true) {
            String userInput = scanner.nextLine();
            String[] splitInput = userInput.split("\\s+");

            System.out.println("##output-splitInput: " + Arrays.toString(splitInput));

            // useless
            // might delete second constructor and find different solution

            if (splitInput[0].equalsIgnoreCase("expense-tracker")
                    && splitInput[1].equalsIgnoreCase("add")
                    && splitInput[2].equalsIgnoreCase("--description")) {


                Pattern pattern = Pattern.compile("\"([^\"]*)\"");
                Matcher matcher = pattern.matcher(userInput);

                if (matcher.find()) {
                    description = matcher.group(1);
                    System.out.println(description);
                }

            }

            // works for now, should change it in the future
            if (!(splitInput[splitInput.length - 1].equalsIgnoreCase("list")
                    || splitInput[splitInput.length - 1].equalsIgnoreCase("summary"))) {
                value = Integer.parseInt(splitInput[splitInput.length - 1]);
                System.out.println("##output-value: " + value);

            } else {
                if (splitInput[0].equalsIgnoreCase("expense-tracker") &&
                        splitInput[1].equalsIgnoreCase("list")) {
                    for (Expenses expenses : expensesList) {
                        System.out.println(expenses);
                    }
                }
                if (splitInput[0].equalsIgnoreCase("expense-tracker") &&
                        splitInput[1].equalsIgnoreCase("summary")) {
                    System.out.println("Total expenses: $" + Expenses.getSumAmount());
                }
            }

                expensesList.add(new Expenses(description, value));

        }

    }
}
