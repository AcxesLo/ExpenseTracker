package main;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static String description;
    public static int value;
    public static List<Expenses> expensesList = new ArrayList<>();

    public static void main(String[] args) {

        //TODO
        // - fix issue with having a too high int value
        // - ID, Date, Description and Amount Strings must match with the actual values space wise at the list command
        /*
        in this format:
        # ID  Date       Description  Amount
        # 1   2024-08-06  Lunch        $20
        # 2   2024-08-06  Dinner       $10
         */

        Scanner scanner = new Scanner(System.in);

        // expense-tracker add --description "hello world" --amount 123123

        while (true) {
            String userInput = scanner.nextLine();
            String[] splitInput = userInput.split("\\s+");

            System.out.println("##output-splitInput: " + Arrays.toString(splitInput));

            // useless
            // might delete second constructor and find different solution

            if (splitInput[0].equalsIgnoreCase("expense-tracker")
                    && splitInput[1].equalsIgnoreCase("add")
                    && splitInput[2].equalsIgnoreCase("--description")
                    && splitInput[splitInput.length - 2].equalsIgnoreCase("--amount")
                    && isNumeric(splitInput[splitInput.length - 1])) {

                value = Integer.parseInt(splitInput[splitInput.length - 1]);
                System.out.println("##output-value: " + value);

                /*
                regex
                -- \" -> catches the first double quote -> "
                -- ([^"]*) -> capturing a group that matches -> [^"]*,
                zero or more characters that are not a double quote -> "
                -- \" catches the closing double quote -> "
                 */
                Pattern pattern = Pattern.compile("\"([^\"]*)\"");
                Matcher matcher = pattern.matcher(userInput);

                if (matcher.find()) {
                    // retrieves the text by the first parentheses group, the -> ([^"]*) part
                    // group 0 would give me the string included with the double quotes
                    description = matcher.group(1);
                    System.out.println(description);
                }
                expensesList.add(new Expenses(description, value));
            }

            // works for now, should change it in the future
//            if (splitInput[splitInput.length - 2].equalsIgnoreCase("--amount")
//                    && isNumeric(splitInput[splitInput.length - 1])) {
//                value = Integer.parseInt(splitInput[splitInput.length - 1]);
//                System.out.println("##output-value: " + value);
//            }
            if (splitInput[0].equalsIgnoreCase("expense-tracker")
                    && splitInput[1].equalsIgnoreCase("delete")
                    && splitInput[splitInput.length - 2].equalsIgnoreCase("--id")
                    && isNumeric(splitInput[splitInput.length - 1])) {
                int targetID = Integer.parseInt(splitInput[splitInput.length - 1]);
                boolean removed = expensesList.removeIf(expense -> expense.getExpenseID() == targetID);

                if (removed) {
                    System.out.println("The expense entry has been removed.");
                } else {
                    System.out.println("There is no entry with this ID");
                }
            }
            if (splitInput[0].equalsIgnoreCase("expense-tracker") &&
                    splitInput[1].equalsIgnoreCase("list")) {
                for (Expenses expenses : expensesList) {
                    System.out.println(expenses);
                }
            }
            if (splitInput[0].equalsIgnoreCase("expense-tracker")
                    && splitInput[1].equalsIgnoreCase("summary")
                    && !splitInput[2].equalsIgnoreCase("--month")) {
                System.out.println("Total expenses: $" + Expenses.getSumAmount());
            }
            if (splitInput[0].equalsIgnoreCase("expense-tracker")
                    && splitInput[1].equalsIgnoreCase("summary")
                    && splitInput[splitInput.length - 2].equalsIgnoreCase("--month")
                    && isNumeric(splitInput[splitInput.length - 1])) {

                /*
                regex
                checks if the input matches the numbers 1-12
                 */
                if (splitInput[splitInput.length - 1].matches("^(1[0-2]|[1-9])$")) {

                    switch (splitInput[splitInput.length - 1]) {
                        case "1":
                            System.out.println("Total expenses for January: ");
                            break;
                        case "2":
                            System.out.println("Total expenses for February: ");
                            break;
                        case "3":
                            System.out.println("Total expenses for March: ");
                            break;
                        case "4":
                            System.out.println("Total expenses for April: ");
                            break;
                        case "5":
                            System.out.println("Total expenses for May: ");
                            break;
                        case "6":
                            System.out.println("Total expenses for June: ");
                            break;
                        case "7":
                            System.out.println("Total expenses for July: ");
                            break;
                        case "8":
                            System.out.println("Total expenses for August: ");
                            break;
                        case "9":
                            System.out.println("Total expenses for September: ");
                            break;
                        case "10":
                            System.out.println("Total expenses for October: ");
                            break;
                        case "11":
                            System.out.println("Total expenses for November: ");
                            break;
                        case "12":
                            System.out.println("Total expenses for December: ");
                            break;
                    }

                    List<Expenses> filtered = expensesList.stream()
                            .filter(expenses -> expenses.getLocalDate().getMonthValue()
                                    == Integer.parseInt(splitInput[splitInput.length - 1]))
                            .toList();

                    filtered.forEach(expenses -> System.out.println(expenses.toString()));

                    int total = filtered.stream()
                            .mapToInt(Expenses::getAmount)
                            .sum();

                    System.out.println("Total amount: $" + total);


                }

            }
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
