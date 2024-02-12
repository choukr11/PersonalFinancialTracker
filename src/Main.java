import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {
        int choice;

        try (Scanner sc = new Scanner(System.in)) {
            PersonalFinancialTracker financialTracker = new PersonalFinancialTracker();
            do {
                System.out.println("""

                            Personal Financial Tracker.
                        ----------------------------------                 \s
                        1. Add Income.
                        2. Add Expense.
                        3. Display Transactions History.
                        4. Generate a Full report (report.txt).
                        5. Exit!
                        ----------------------------------
                        """);
                try {
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    throw new RuntimeException(e);
                }
                switch (choice) {
                    case 1:
                        financialTracker.addIncome();
                        financialTracker.transaction();
                        break;
                    case 2:
                        financialTracker.addExpense();
                        financialTracker.transaction();
                        break;
                    case 3:
                        financialTracker.printWriter.close();
                        financialTracker.displayTransactionsHistory();
                        break;
                    case 4:
                        financialTracker.printWriter.close();
                        financialTracker.getReport();
                        break;
                    case 5:
                        financialTracker.printWriter.close();
                        financialTracker.bufferedReader.close();
                        System.exit(0);
                    default:
                        return;
                }
            } while (true);
        } catch (IOException e) {
            System.err.println("I/O Exception: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.err.println("Input Mismatch: : " + e.getMessage());
        }
    }
}