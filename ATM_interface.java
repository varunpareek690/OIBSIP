import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class ATM_interface {
    private static Map<String, User> users = new HashMap<>();
    private static User currentUser;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeUsers();
        authenticateUser();

        boolean TRUE = true;
        while (TRUE) {
            displayMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    TransactionHistory.showTransactionHistory(currentUser);
                    break;
                case 2:
                    Withdrawal.performWithdrawal(currentUser, scanner);
                    break;
                case 3:
                    Deposit.performDeposit(currentUser, scanner);
                    break;
                case 4:
                    Transfer.performTransfer(currentUser, users, scanner);
                    break;
                case 5:
                    TRUE = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Thank you for using the ATM.");
    }

    private static void initializeUsers() {
        List<Transaction> user1Transactions = new ArrayList<>();
        List<Transaction> user2Transactions = new ArrayList<>();
        List<Transaction> user3Transactions = new ArrayList<>();
        List<Transaction> user4Transactions = new ArrayList<>();
        List<Transaction> user5Transactions = new ArrayList<>();
        List<Transaction> user6Transactions = new ArrayList<>();
        List<Transaction> user7Transactions = new ArrayList<>();
        List<Transaction> user8Transactions = new ArrayList<>();
        List<Transaction> user9Transactions = new ArrayList<>();
        List<Transaction> user10Transactions = new ArrayList<>();

        users.put("user123", new User("user123", "1234", 1000, user1Transactions));
        users.put("user456", new User("user456", "5678", 500, user2Transactions));
        users.put("user789", new User("user789", "3453", 500, user3Transactions));
        users.put("user111", new User("user111", "2341", 500, user4Transactions));
        users.put("user222", new User("user222", "5555", 500, user5Transactions));
        users.put("user333", new User("user333", "7654", 500, user6Transactions));
        users.put("user444", new User("user444", "8411", 500, user7Transactions));
        users.put("user555", new User("user555", "4520", 500, user8Transactions));
        users.put("user666", new User("user666", "1300", 500, user9Transactions));
        users.put("user777", new User("user777", "7619", 500, user10Transactions));
    }

    private static void authenticateUser() {
        int maxAttempts = 3;
        for (int attempts = 1; attempts <= maxAttempts; attempts++) {
            System.out.print("Enter user id: ");
            String userId = scanner.next();
            System.out.print("Enter pin: ");
            String pin = scanner.next();

            currentUser = users.get(userId);

            if (currentUser != null && currentUser.authenticate(pin)) {
                displaySuccessMessage("Authentication successful. Welcome, " + userId + "!");
                return;
            } else {
                System.out.println("Authentication failed. Attempts left: " + (maxAttempts - attempts));
            }
        }

        // If all attempts fail, display a message and exit
        displayColoredMessage("Visit your nearest bank for assistance.", "RED");
        System.exit(1);
    }

    private static void displayColoredMessage(String message, String color) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED_BOLD = "\u001B[1;31m";

        switch (color.toUpperCase()) {
            case "RED":
                System.out.println(ANSI_RED_BOLD + message + ANSI_RESET);
                break;
            default:
                System.out.println(message);
        }
    }

    private static void displaySuccessMessage(String message) {
        String ANSI_GREEN_BOLD = "\u001B[1;32m";
        String ANSI_RESET = "\u001B[0m";

        System.out.println(ANSI_GREEN_BOLD + message + ANSI_RESET);
    }

    private static void displayMenu() {
        System.out.println("ATM Menu:");
        System.out.println("1. Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class User {
    private String uID;
    private String pin;
    private double balance;
    private List<Transaction> transactionHistory;

    public User(String uID, String pin, double balance, List<Transaction> transactionHistory) {
        this.uID = uID;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = transactionHistory;
    }

    public boolean authenticate(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public void updateBalance(double amount) {
        balance += amount;
    }

    public String getuID() {
        return uID;
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}

class TransactionHistory {
    public static void showTransactionHistory(User user) {
        System.out.println("Transaction history for user: " + user.getuID());

        List<Transaction> transactions = user.getTransactionHistory();

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction history:");
            for (Transaction transaction : transactions) {
                System.out.println("Type: " + transaction.getType() + ", Amount: ₹" + transaction.getAmount());
            }
        }
    }
}

class Withdrawal {
    public static void performWithdrawal(User user, Scanner scanner) {
        System.out.println("Withdrawal functionality");

        System.out.print("Enter withdrawal amount: ");
        double withdrawalAmount = scanner.nextDouble();

        if (withdrawalAmount > 0 && withdrawalAmount <= user.getBalance()) {
            user.updateBalance(-withdrawalAmount);
            user.addTransaction(new Transaction("Withdrawal", withdrawalAmount));

            System.out.println("Withdrawal of ₹" + withdrawalAmount + " successful");
            System.out.println("New balance: ₹" + user.getBalance());
        } else {
            System.out.println("Could not withdraw, Please check your balance and try again.");
        }
    }
}

class Deposit {
    public static void performDeposit(User user, Scanner scanner) {
        System.out.println("Deposit functionality");

        System.out.print("Enter deposit amount: ");
        double depositAmount = scanner.nextDouble();

        if (depositAmount > 0) {
            user.updateBalance(depositAmount);
            user.addTransaction(new Transaction("Deposit", depositAmount));

            System.out.println("Deposit of ₹" + depositAmount + " successful");
            System.out.println("New balance: ₹" + user.getBalance());
        } else {
            System.out.println("Amount entered is not valid, Please check again.");
        }
    }
}

class Transfer {
    public static void performTransfer(User currentUser, Map<String, User> users, Scanner scanner) {
        System.out.println("Transfer functionality");

        System.out.print("Enter recipient's user ID: ");
        String recipientUserId = scanner.next();

        User recipient = users.get(recipientUserId);
        if (recipient == null) {
            System.out.println("Recipient not found. Please check the user ID and try again.");
            return;
        }

        System.out.print("Enter your PIN to confirm the transfer: ");
        String pinConfirmation = scanner.next();

        if (!currentUser.authenticate(pinConfirmation)) {
            System.out.println("PIN confirmation failed. Transfer canceled.");
            return;
        }

        System.out.print("Enter transfer amount: ");
        double transferAmount = scanner.nextDouble();

        if (transferAmount > 0 && transferAmount <= currentUser.getBalance()) {
            currentUser.updateBalance(-transferAmount);
            recipient.updateBalance(transferAmount);

            currentUser.addTransaction(new Transaction("Transfer to " + recipient.getuID(), transferAmount));
            recipient.addTransaction(new Transaction("Transfer from " + currentUser.getuID(), transferAmount));

            System.out.println("Transfer of ₹" + transferAmount + " to user " + recipient.getuID() + " successful");
            System.out.println("Your new balance: ₹" + currentUser.getBalance());
            System.out.println("Recipient's new balance: ₹" + recipient.getBalance());
        } else {
            System.out.println("Invalid transfer amount. Please check your balance and try again.");
        }
    }
}
