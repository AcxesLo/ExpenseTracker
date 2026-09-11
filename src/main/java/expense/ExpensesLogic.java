package expense;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpensesLogic {
    private String description;


    public static final List<Expenses> expensesList = new ArrayList<>();
    private String rowFormat;

    public void addFunction(String userInput, String[] splitInput) {
        int amount = Integer.parseInt(splitInput[splitInput.length - 1]);

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
            System.out.println("Expense \"" + description + "\" has been added.");
        }
        expensesList.add(new Expenses(description, amount));
    }

    public void deleteFunction(String[] splitInput) {

        int targetId = Integer.parseInt(splitInput[splitInput.length - 1]);

        Expenses toRemove = expensesList.stream()
                .filter(expenses -> expenses.getExpenseID() == targetId)
                .findFirst()
                .orElse(null);

        if (toRemove != null) {
            Expenses.subtractFromSum(toRemove.getAmount());
            expensesList.remove(toRemove);
            System.out.println("The expense entry with the ID " + targetId + " has been removed.");
        } else {
            System.out.println("There is no entry with this ID");
        }
    }

    public void listFunction() {
        alignmentFormat();
        printHeader();
        formattedLoop(rowFormat, expensesList);
    }

    public void summaryFunction() {
        System.out.println("Total expenses: $" + Expenses.getSumAmount());
    }

    public void summaryByMonthFunction(String[] splitInput) {
        // regex checks if the input matches the numbers 1-12
        if (splitInput[splitInput.length - 1].matches("^(1[0-2]|[1-9])$")) {

            int monthValue = Integer.parseInt(splitInput[splitInput.length - 1]);
            Month month = Month.of(monthValue);
            String monthName = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            System.out.println("Total expenses for " + monthName + ":");

            alignmentFormat();
            printHeader();

            List<Expenses> filteredByDate = expensesList.stream()
                    .filter(expenses -> expenses.getLocalDate().getMonthValue()
                            == Integer.parseInt(splitInput[splitInput.length - 1]))
                    .toList();

            formattedLoop(rowFormat, filteredByDate);

            int total = filteredByDate.stream()
                    .mapToInt(Expenses::getAmount)
                    .sum();

            System.out.println("Total amount: $" + total);
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

    public void alignmentFormat() {
        // streams through the list to find the longest description
        // converts stream from Stream<Expenses> into an IntStream
        // from {"Lunch", "Dinner", "A longer description"} to ->  {5, 6, 20}
        int descriptionWidth = expensesList.stream()
                .mapToInt(e -> e.getDescription().length())
                .max()
                .orElse(0);

        // guarantees the column is at least as wise as the header "Description"
        descriptionWidth = Math.max(descriptionWidth, "Description".length());

        //  - ID, Date (fixed) -> %-4s/%-12s left aligned, minimum width of 4 and 12
        //  - Description -> based on the max length,
        // value gotten from the stream (would be %-20s with the example above)
        //  - Amount -> %s -> inserts the string as is, no padding %n -> new line
        rowFormat = "%-4s %-12s %-" + (descriptionWidth + 2) + "s %s%n";
    }

    public void formattedLoop(String rowFormat, List<Expenses> expensesList) {
        for (Expenses expenses : expensesList) {
            System.out.printf(rowFormat
                    , expenses.getExpenseID()
                    , expenses.getLocalDate()
                    , expenses.getDescription()
                    , "$" + expenses.getAmount());
        }
    }

    public void printHeader() {
        System.out.printf(rowFormat, "ID", "Date", "Description", "Amount");
    }

    public void printCommands() {
        System.out.println("<Commands> \n"
                + "expense-tracker add --description {argument} --amount {value}\n"
        + "expense-tracker delete --id {value}\n"
        + "expense-tracker list\n"
        + "expense-tracker summary\n"
        + "expense-tracker summary --month {value}");
    }

    public List<Expenses> getExpensesList() {
        return expensesList;
    }
}
