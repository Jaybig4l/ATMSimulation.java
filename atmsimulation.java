import java.util.Scanner;

public class ATMSimulation {

    public static void main(String[] args) {

        final int CORRECT_PIN = 1234;
        final int MAX_ATTEMPTS = 3;
        final int MAX_TRANSACTIONS = 100;

        Scanner input = new Scanner(System.in);

        double balance = 0.0;
        String[] history = new String[MAX_TRANSACTIONS];
        int transactionCount = 0;

        // ----- Authentication: keep asking until correct PIN or max attempts -----
        int attempts = 0;
        boolean loggedIn = false;

        while (attempts < MAX_ATTEMPTS && !loggedIn) {
            System.out.print("Enter your PIN: ");
            int pin = input.nextInt();

            if (pin == CORRECT_PIN) {
                loggedIn = true;
            } else {
                attempts++;
                int remaining = MAX_ATTEMPTS - attempts;
                if (remaining > 0) {
                    System.out.println("Incorrect PIN. Attempts left: " + remaining);
                }
            }
        }

        if (!loggedIn) {
            System.out.println("Too many incorrect attempts. Card blocked.");
        } else {
            System.out.println("Login successful. Welcome!");

            // ----- Main menu: repeat until the user chooses to exit -----
            int choice = 0;
            do {
                System.out.println();
                System.out.println("===== ATM MENU =====");
                System.out.println("1. Check balance");
                System.out.println("2. Deposit money");
                System.out.println("3. Withdraw money");
                System.out.println("4. View transactions");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                choice = input.nextInt();

                if (choice == 1) {
                    System.out.printf("Current balance: $%.2f%n", balance);

                } else if (choice == 2) {
                    System.out.print("Enter deposit amount: ");
                    double amount = input.nextDouble();

                    if (amount <= 0) {
                        System.out.println("Error: amount must be greater than zero.");
                    } else if (transactionCount >= MAX_TRANSACTIONS) {
                        System.out.println("Error: transaction history is full.");
                    } else {
                        balance += amount;
                        history[transactionCount] = String.format("Deposit:    +$%.2f", amount);
                        transactionCount++;
                        System.out.printf("Deposited $%.2f. New balance: $%.2f%n", amount, balance);
                    }

                } else if (choice == 3) {
                    System.out.print("Enter withdrawal amount: ");
                    double amount = input.nextDouble();

                    if (amount <= 0) {
                        System.out.println("Error: amount must be greater than zero.");
                    } else if (amount > balance) {
                        System.out.println("Error: insufficient funds.");
                    } else if (transactionCount >= MAX_TRANSACTIONS) {
                        System.out.println("Error: transaction history is full.");
                    } else {
                        balance -= amount;
                        history[transactionCount] = String.format("Withdrawal: -$%.2f", amount);
                        transactionCount++;
                        System.out.printf("Withdrew $%.2f. New balance: $%.2f%n", amount, balance);
                    }

                } else if (choice == 4) {
                    if (transactionCount == 0) {
                        System.out.println("No transactions yet.");
                    } else {
                        System.out.println("--- Transaction History ---");
                        for (int i = 0; i < transactionCount; i++) {
                            System.out.println((i + 1) + ". " + history[i]);
                        }
                    }

                } else if (choice == 5) {
                    System.out.println("Thank you for using the ATM. Goodbye!");

                } else {
                    System.out.println("Invalid option. Please choose 1-5.");
                }

            } while (choice != 5);
        }

        input.close();
    }
}
