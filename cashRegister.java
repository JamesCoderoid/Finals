import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class cashRegister {

    static Scanner scan = new Scanner(System.in);
    static ArrayList<String> products = new ArrayList<String>();
    static ArrayList<Double> prices = new ArrayList<Double>();
    static ArrayList<Integer> quantities = new ArrayList<Integer>();
    static ArrayList<String> passwords = new ArrayList<String>();
    static ArrayList<String> usernames = new ArrayList<String>(); 
    static String loggedInUser = "";
    static boolean isRunning = true;

    public static void main(String[] args) {
        System.out.println("Welcome to my Store");
        System.out.println("Please Sign up and Log in to enter");

        while(true) {
            System.out.println("1:Sign up");
            System.out.println("2:Log in");
            String choice = "";
            try {
                choice = scan.nextLine();
            } catch (Exception e) {
                System.out.println("Input error. Please try again.");
                continue;
            }

            if (choice.equals("1")) {
                signUp();          
            } else  if (choice.equals("2")) {
                if (logIn()) {
                    break;
                }
            } else {
                System.out.println("Invalid choice, please try again!");
            } 
        }

        while (isRunning) {
            System.out.println("--------------------------");
            System.out.println("--                      --");
            System.out.println("-- Welcome to Pes Store --");
            System.out.println("--                      --");
            System.out.println("--------------------------");
            System.out.println("What would you like to do? ");
            System.out.println("1. Add Product");
            System.out.println("2. Display Products");
            System.out.println("3. Delete Product");
            System.out.println("4. Proceed to Payment");
            System.out.println("5. Update the Quantity");
            System.out.println("6. Remove One Order");
            System.out.println("7. Exit ");
            System.out.println("--------------------------");
            System.out.print("Choose an option: ");

            int option = 0;
            try {
                option = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scan.nextLine();
                continue;
            }

            switch (option) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    displayProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    proceedToPayment();
                    break;
                case 5:
                    updateQuantity();
                    break;
                case 6:
                    removeOneOrder();
                    break;
                case 7: 
                    System.out.println("Thank you for using the register!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice, Try again.");
                    break;
            }

            if (isRunning) {
                System.out.print("Do you want to continue another transaction? (y/n): ");
                try {
                    String response = scan.nextLine();
                    if (response.equalsIgnoreCase("n")) {
                        isRunning = false;
                        System.out.println("Thank you for using the register! Please Comeback  ");
                    }
                } catch (Exception e) {
                    System.out.println("Input error. Exiting transaction loop.");
                    isRunning = false;
                }
            }
        }
    }

    public static void signUp() {
        String username;
        String password;
        Pattern usernamePattern = Pattern.compile("^\\w{10,15}$");
        Pattern passwordPattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)\\w{8,}$");

        boolean valid = false;

        while(!valid) {
            try {
                System.out.print("Create username that has aplanumberic 10-15 characters \n");
                System.out.print("Create username: ");
                username = scan.nextLine();

                System.out.print("Create password that has at least 8 characters, 1 uppercase, 1 digit \n");
                System.out.print("Create password: ");
                password = scan.nextLine();

                Matcher userMatcher = usernamePattern.matcher(username);
                Matcher pasMatcher = passwordPattern.matcher(password);

                if (userMatcher.matches() && pasMatcher.matches()) {
                    usernames.add(username);
                    passwords.add(password);
                    System.out.println("Sign up successfully. Please LogIn");
                    valid = true;
                } else {
                    System.out.println("Sign up failed due to not following the instructions.");
                    if (!userMatcher.matches()) {
                        System.out.println("Username must be 10–15 characters long and contain only letters, digits, or underscores.");
                    }
                    if (!pasMatcher.matches()) {
                        System.out.println("Password must be 8 or more characters, with at least 1 uppercase letter and 1 digit.");
                    }
                    System.out.println("Please try again.\n");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
                scan.nextLine();
            }
        }
    }

    public static boolean logIn() {
        try {
            System.out.print("Enter username: ");
            String username = scan.nextLine();
            System.out.print("Enter password: ");
            String password = scan.nextLine();

            for (int i = 0; i < usernames.size(); i++) {
                if (usernames.get(i).equals(username) && passwords.get(i).equals(password)) {
                    loggedInUser = username;
                    System.out.println("Login Successfully\n");
                    return true;
                }
            }
            System.out.println("Invalid Credentials. Try Again\n");
        } catch (Exception e) {
            System.out.println("An error occurred during login. Please try again.");
        }
        return false;
    }

    public static void addProduct() {
        try {
            System.out.print("Enter the product name: ");
            String name = scan.nextLine();
            System.out.print("Enter the price: ");
            double price = scan.nextDouble();
            scan.nextLine();
            System.out.print("Enter the quantity: ");
            int quantity = scan.nextInt();
            scan.nextLine();

            products.add(name);
            prices.add(price);
            quantities.add(quantity);
            System.out.println("Product added successfully!\n");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Product not added.\n");
            scan.nextLine();
        }
    }

    public  static void displayProduct() {
        if (products.isEmpty()) {
            System.out.println("No products available.\n");
            return;
        }

        System.out.println("\nProducts in the store:");
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i) + " - PHP" + prices.get(i) + " - Stock: " + quantities.get(i));
        }
        System.out.println();
    }

    public static void deleteProduct() {
        if (products.isEmpty()) {
            System.out.println("No products to delete.\n");
            return;
        }

        displayProduct();
        System.out.print("Enter the product number to remove: ");
        try {
            int index = scan.nextInt();
            scan.nextLine();
            if (index < 1 || index > products.size()) {
                System.out.println("Invalid product number!\n");
                return;
            }
            products.remove(index - 1);
            prices.remove(index - 1);
            quantities.remove(index - 1);
            System.out.println("Product removed successfully!\n");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.\n");
            scan.nextLine();
        }
    }

    public static void proceedToPayment() {
        if (products.isEmpty()) {
            System.out.println("No products available for payment.\n");
            return;
        }

        double total = 0;
        for (int i = 0; i < products.size(); i++) {
            total += prices.get(i) * quantities.get(i);
        }

        System.out.println("Total price: PHP" + total);
        System.out.print("Enter payment amount: PHP");
        try {
            double payment = scan.nextDouble();
            scan.nextLine();
            if (payment < total) {
                System.out.println("Insufficient payment. Transaction canceled.\n");
                return;
            }

            double change = payment - total;
            System.out.println("Payment successful! Change: PHP" + change);

            LogTransaction(total);

            products.clear();
            prices.clear();
            quantities.clear();
            System.out.println("Inventory cleared for the next transaction.\n");
        } catch (InputMismatchException e) {
            System.out.println("Invalid payment input.\n");
            scan.nextLine();
        }
    }

    public static void updateQuantity() {
        if (products.isEmpty()) {
            System.out.println("There is no product available to update. Please check again!! ");
            return;
        }

        displayProduct();
        System.out.print("Enter the product number you want to edit the quantity: ");
        try {
            int index = scan.nextInt();
            scan.nextLine();
            if (index < 1 || index > products.size()) {
                System.out.println("Product number can't be found\n");
                return;
            }
            System.out.print("Enter the new quantity: ");
            int nQuantity = scan.nextInt();
            scan.nextLine();
            if (nQuantity < 0) {
                System.out.println("The quantity cannot be negative!\n");
                return;
            }
            quantities.set(index - 1, nQuantity);
            System.out.println("The product quantity updated successfully\n");
            displayProduct();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.\n");
            scan.nextLine();
        }
    }

    public static void removeOneOrder() {
        if (products.isEmpty()) {
            System.out.println("There is no order to remove.\n");
            return;
        }

        displayProduct();
        System.out.print("Enter the product you want to remove: ");
        try {
            int index = scan.nextInt();
            scan.nextLine();
            if (index < 1 || index > products.size()) {
                System.out.println("Invalid product number.\n");
                return;
            }
            System.out.println(products.get(index - 1) + " has been removed from the order.");
            products.remove(index - 1);
            prices.remove(index - 1);
            quantities.remove(index - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.\n");
            scan.nextLine();
        }
    }

    public static void LogTransaction(double total) {
        try {
            FileWriter writer = new FileWriter("transactions.txt", true);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTime = LocalDateTime.now().format(formatter);

            writer.write("=== Transaction ===\n");
            writer.write("Date & Time: " + dateTime + "\n");
            writer.write("Cashier: " + loggedInUser + "\n");
            writer.write("Items:\n");

            for (int i = 0; i < products.size(); i++) {
                writer.write("- " + products.get(i) + " | Quantity: " + quantities.get(i) + " | Price: PHP" + prices.get(i) + "\n");   
            }

            writer.write("Total: PHP" + total + "\n");
            writer.write("-------------------------\n");
            writer.close();
            System.out.println("Transaction logged successfully.");
        } catch (IOException e) {
            System.out.println("An error occured while logging the transaction.");
        }
    }
}
