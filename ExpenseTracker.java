import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class ExpenseTracker {
    Scanner sc = new Scanner(System.in);
    int count = 0;
    String[] names = new String[100];
    int[] amounts = new int[100];
    String[] categories = new String[100];

    void addExpense() {
        System.out.print("Enter expense name: ");
        names[count] = sc.next();
        System.out.print("Enter amount (INR): ");
        amounts[count] = sc.nextInt();
        System.out.print("Enter category (Food, Travel, Shopping, Bills, Others): ");
        categories[count] = sc.next();
        count++;
        System.out.println("✅ Expense Added: " + names[count - 1] + " - " + amounts[count - 1] + " (" + categories[count - 1] + ")");
        saveToFile(count - 1);
    }

    void viewAllExpenses() {
        System.out.println("All Expenses:");
        for (int i = 0; i < count; i++) {
            System.out.println(names[i] + " - " + amounts[i] + " (" + categories[i] + ")");
        }
    }

    void viewByCategory() {
        System.out.print("Enter category to filter (Food, Travel, Shopping, Bills, Others): ");
        String filterCategory = sc.next();
        System.out.println("Expenses in category: " + filterCategory);
        for (int i = 0; i < count; i++) {
            if (categories[i].equalsIgnoreCase(filterCategory)) {
                System.out.println(names[i] + " - " + amounts[i] + " (" + categories[i] + ")");
            }
        }
    }

    void showTotal() {
        int total = 0;
        for (int i = 0; i < count; i++) {
            total += amounts[i];
        }
        System.out.println("Total Expenses: INR " + total);

        System.out.println("💰 Set your budget limit to manage expenses effectively!");
        System.out.print("Enter your budget limit (INR): ");
        int budgetLimit = sc.nextInt();

        if (total > budgetLimit) {
            System.out.println("⚠️ You have exceeded your budget limit of INR " + budgetLimit);
        } else {
            System.out.println("✅ You are within your budget limit of INR " + budgetLimit);
        }
    }

    void showBiggestExpense() {
        int max = amounts[0];
        int pos = 0;
        for (int i = 1; i < count; i++) {
            if (amounts[i] > max) {
                max = amounts[i];
                pos = i;
            }
        }
        System.out.println("Biggest Expense: " + names[pos] + " - INR " + amounts[pos] + " (" + categories[pos] + ")");
    }

    void loadFromFile() {
        try {
            File file = new File("expenses.txt");
            if (!file.exists()) {
                return; // No file yet
            }
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    names[count] = parts[0];
                    amounts[count] = Integer.parseInt(parts[1]);
                    categories[count] = parts[2];
                    count++;
                }
            }
            fileScanner.close();
            System.out.println("📂 Loaded " + count + " expenses from file.");
        } catch (Exception e) {
            System.out.println("❌ Error loading file: " + e.getMessage());
        }
    }

    void saveToFile(int index) {
        try {
            FileWriter fw = new FileWriter("expenses.txt", true); // append mode
            fw.write(names[index] + "," + amounts[index] + "," + categories[index] + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("❌ Error saving expense to file: " + e.getMessage());
        }
    }

    void menu() {
        while (true) {
            System.out.println("\n--- Elite Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. View by Category");
            System.out.println("4. Show Total + Budget Status");
            System.out.println("5. Show Biggest Expense");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("⚠️ Invalid input. Please enter a number.");
                sc.next(); // clear wrong input
                continue;
            }
            int choice = sc.nextInt();

            switch (choice) {
                case 1: addExpense(); break;
                case 2: viewAllExpenses(); break;
                case 3: viewByCategory(); break;
                case 4: showTotal(); break;
                case 5: showBiggestExpense(); break;
                case 6: System.out.println("Exiting... Goodbye!"); return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker();
        tracker.loadFromFile();
        tracker.menu();
    }
}
