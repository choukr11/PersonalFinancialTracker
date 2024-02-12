import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class PersonalFinancialTracker implements Serializable {
    //Scanner to get data from the user.
    transient Scanner sc = new Scanner(System.in);
    //All the Attributes
    int choice = 0;
    double amount;
    double expenses;
    double food;
    double utilities;
    double rent;
    double otherExpenses;
    double incomes;
    double realEstate;
    double stocks;
    double salary;
    double otherIncomes;
    double netSavings;
    List<Double> sumIncome;
    List<Double> sumExpenses;
    String path;
    String type;
    /*
    * It would've been better to group all the categories and their amounts in array lists,
    *consider it when you come back to this code.
    * */
    String category;
    // Date/Time formatter
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String date = currentDateTime.format(formatter);
    //File path
    File file = new File("transaction.txt");
    //Declared objects to write and read the previous files.
    PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
    //Constructor
    public PersonalFinancialTracker() throws IOException {
    }
    //Method to add an income and its category.
    /*
     *This method is used to add new income to the tracker
     *when the user chose number one on the console,
     * now they have the choice to choose the category of the income,
     * to save time I only gave them 4 categories, while the intention was to have the use
     * add other types of income adn show up on the console, but due to time conflict we chose the simplest code.
     * Same thing for expense method.
     *     * */
    public void addIncome() throws IOException {
        type = "Income";
        System.out.println("""
                       Choose Income Category.
                ----------------------------------                 \s
                1. Salary.
                2. Stocks.
                3. I'm a Landlord!.
                4. Add new income category.
                5. Exit!
                ----------------------------------                                    \s
                """);
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            throw new RuntimeException(e);
        }
        switch (choice) {
            case 1:
                category = "Salary";
                break;
            case 2:
                category = "Stocks";
                break;
            case 3:
                category = "Real-estate";

                break;
            case 4:
                addIncomeCategory();
                break;
            case 5:
                printWriter.close();
                bufferedReader.close();
                System.exit(0);
            default:
                return;
        }
        System.out.println("Enter income amount! (stay positive):");
        do {
            System.out.println("Enter income amount! (enter a positive number):");
            amount = sc.nextDouble();
        }
        while (amount < 0);
        System.out.println("Income added successfully!");
    }

    //Method to add new Income Category.
    private void addIncomeCategory() {
        System.out.println("Enter Income Category:");
        try {
            category = sc.nextLine();
            sc.nextLine();
        } catch (InputMismatchException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Income Category added Successfully!");
    }

    //Method to add an expense and its category.
    /*
     * *This method is used to add new income to the tracker
     *when the user chose number one on the console,
     * now they have the choice to choose the category of the income,
     * */
    public void addExpense() throws IOException {
        type = "Expense";
        System.out.println("""
                       \n
                       Choose Expense Category.
                ----------------------------------                 \s
                1. Food.
                2. Utilities.
                3. Rent.
                4. Add new expense category.
                5. Exit!
                ----------------------------------                                    \s
                                
                """);
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            throw new RuntimeException(e);
        }
        switch (choice) {
            case 1:
                category = "Food";
                break;
            case 2:
                category = "Utilities";
                break;
            case 3:
                category = "Rent";

                break;
            case 4:
                addExpenseCategory();
                break;
            case 5:
                printWriter.close();
                bufferedReader.close();
                System.exit(0);
            default:
                return;
        }
        System.out.println("Enter expense amount! (stay positive)");
        do {
            System.out.println("Enter expense amount! (enter a positive number):");
            amount = sc.nextDouble();
        } while (amount < 0);
        //come back later
        System.out.println("Expense added successfully!");
    }

    //Method to add new Expense Category.
    private void addExpenseCategory() {
        System.out.println("Enter Expense Category:");
        category = sc.nextLine();
        sc.nextLine();
        System.out.println("Expense Category added Successfully!");
    }

    public void transaction() {
        printWriter.print(date + "," + type + "," + category + "," + amount + "\n");
    }

    //Method to generate a full financial report.
    public void getReport() throws IOException {
        System.out.println("Enter path file: ");
        path = sc.nextLine();
        FileWriter fileWrite = new FileWriter(path);
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length >= 4) {
                    //By type "Income".
                    if (columns[1].trim().equals("Income")) {
                        incomes += Double.parseDouble(columns[3].trim());
                        //By category
                        switch (columns[2].trim()) {
                            case "Salary" -> salary += Double.parseDouble(columns[3].trim());
                            case "Real-estate" -> realEstate += Double.parseDouble(columns[3].trim());
                            case "Stocks" -> stocks += Double.parseDouble(columns[3].trim());
                            default -> otherIncomes += Double.parseDouble(columns[3].trim());
                        }
                        //By type "Expense".
                    } else if (columns[1].trim().equals("Expense")) {
                        expenses += Double.parseDouble(columns[3].trim());
                        //By category
                        switch (columns[2].trim()) {
                            case "Food" -> food += Double.parseDouble(columns[3].trim());
                            case "Utilities" -> utilities += Double.parseDouble(columns[3].trim());
                            case "Rent" -> rent += Double.parseDouble(columns[3].trim());
                            default -> otherExpenses += Double.parseDouble(columns[3].trim());
                        }
                    }
                }

            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        netSavings = incomes - expenses;
        sumIncome = List.of(salary, realEstate, stocks, otherIncomes);
        sumExpenses = List.of(food, utilities, rent, otherExpenses);
        double maxIn = getMaxValue(sumIncome);
        double maxEx = getMaxValue(sumExpenses);
        String maxIncome;
        String maxExpenses;
        switch (sumIncome.indexOf(maxIn)) {
            case 0 -> maxIncome = "Salary.";
            case 1 -> maxIncome = "Real-estate.";
            case 2 -> maxIncome = "Stocks.";
            case 3 -> maxIncome = "Other incomes.";
            default -> throw new IllegalStateException("Unexpected value: " + sumIncome);
        }
        maxExpenses = switch (sumExpenses.indexOf(maxEx)) {
            case 0 -> "Food.";
            case 1 -> "Utilities.";
            case 2 -> "Rent.";
            case 3 -> "Other expenses.";
            default -> throw new IllegalStateException("Unexpected value: ");
        };
        fileWrite.write("    Report - Personal Financial Tracker\n" +
                "------------------------------------------\n" +
                "    ------------------------------------\n" +
                "\n" +
                "1. Income Report:\n" +
                "*Salary:" + salary + " - " + String.format("%.2f", salary / incomes * 100) + "% /total income.\n" +
                "*Stocks: " + stocks + " - " + String.format("%.2f", stocks / incomes * 100) + "% /total income.\n" +
                "*Real-estate: " + realEstate + " - " + String.format("%.2f", realEstate / incomes * 100) + "% /total income.\n"
                + "*Other income: " + otherIncomes + " - " + String.format("%.2f", otherIncomes / incomes * 100) + "% /total income.\n"
                + "Your biggest income was your " +
                maxIncome + "." +
                "\n*** Total income: " + incomes + "\n"
                + "    ------------\n" +
                "\n" +
                "2. Expenses Report:\n" +
                "*Food: " + food + " - " + String.format("%.2f", food / expenses * 100) + "\n" +
                "*Utilities: " + utilities + " - " + String.format("%.2f", utilities / expenses * 100) + "\n" +
                "*Rent: " + rent + " - " + String.format("%.2f", rent / expenses * 100) + "\n"
                + "*Other expenses: " + otherExpenses + " - " + String.format("%.2f", otherExpenses / expenses * 100) + "\n"
                + "You Spent the most of your income on " + maxExpenses +
                "\n*** Total expenses: " + expenses + "\n" +
                "    --------------\n" +
                "3. Net Savings = " + String.format("%.2f", incomes - expenses) + "\n" +
                "You spent " + String.format("%.2f", expenses / incomes * 100) + "% of your total income.\n" +
                "You saved " + String.format("%.2f", netSavings / incomes * 100) + "% from your total income.\n" +
                "    ------------------------------------\n"
                + "------------------------------------------\n"
        );
        fileWrite.flush();
        fileWrite.close();
    }
    //Method to Display Transactions History.
    public void displayTransactionsHistory() {
        System.out.println("\n---------------------------------- \n");
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length >= 4) {
                    String date = columns[0].trim();
                    String type = columns[1].trim();
                    String category = columns[2].trim();
                    String amount = columns[3].trim();
                    System.out.println(date + " added " + type + " labeled as " + category + " * " + amount + " MAD/TTC\n");
                    System.out.println("\n---------------------------------- \n");
                } else {
                    System.err.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public static double getMaxValue(List<Double> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty or null");
        }
        // Initialize max with the first element
        double max = list.getFirst();
        int maxIndex = 0;
        // Iterate over the list to find the maximum value
        for (double num : list) {
            if (num > max) {
                max = num;
            }
        }
        // Iterate over the list to find the maximum value and its index
        System.out.println("max index: " + maxIndex);
        return max;
    }
}