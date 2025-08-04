package utility;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.Calendar;

public class InputValidator {
    
    public static int getValidInt(Scanner scanner, int min, int max, String prompt) {
        int input;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number between " + min + " and " + max + ": ");
                scanner.next();
            }
            input = scanner.nextInt();
            scanner.nextLine();
            
            if (input < min || input > max) {
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            }
        } while (input < min || input > max);
        
        return input;
    }
    
    public static int getValidInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input! Please enter a number: ");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }
    
    public static double getValidDouble(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input! Please enter a number: ");
            scanner.next();
        }
        double input = scanner.nextDouble();
        scanner.nextLine();
        return input;
    }
    
    public static boolean getValidBoolean(Scanner scanner, String prompt) {
        System.out.print(prompt + " (true/false): ");
        String input = scanner.nextLine().toLowerCase();
        while (!input.equals("true") && !input.equals("false")) {
            System.out.print("Invalid input! Please enter 'true' or 'false': ");
            input = scanner.nextLine().toLowerCase();
        }
        return Boolean.parseBoolean(input);
    }
    
    public static String getValidString(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }
    
    public static String getValidStringAllowEmpty(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    public static String getValidPhoneNumber(Scanner scanner, String prompt) {
        String phone;
        Pattern phonePattern = Pattern.compile("^(01[0-9]|03|04|05|06|07|08|09)-?[0-9]{7,8}$");
        
        do {
            System.out.print(prompt);
            phone = scanner.nextLine().trim();
            if (!phonePattern.matcher(phone).matches()) {
                System.out.println("Invalid phone number format. Please use Malaysian format (e.g., 012-3456789 or 0123456789)");
            }
        } while (!phonePattern.matcher(phone).matches());
        
        return phone;
    }
    
    public static String getValidEmail(Scanner scanner, String prompt) {
        String email;
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        
        do {
            System.out.print(prompt);
            email = scanner.nextLine().trim();
            if (!emailPattern.matcher(email).matches()) {
                System.out.println("Invalid email format. Please enter a valid email address.");
            }
        } while (!emailPattern.matcher(email).matches());
        
        return email;
    }
    
    public static String getValidDate(Scanner scanner, String prompt) {
        String date;
        Pattern datePattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(19|20)\\d\\d$");
        
        do {
            System.out.print(prompt + " (dd-MM-yyyy): ");
            date = scanner.nextLine().trim();
            if (!datePattern.matcher(date).matches()) {
                System.out.println("Invalid date format. Please use dd-MM-yyyy format (e.g., 25-12-2024)");
            } else if (!isDateValid(date)) {
                System.out.println("Date must be today or later. Please enter a valid date.");
            }
        } while (!datePattern.matcher(date).matches() || !isDateValid(date));
        
        return date;
    }
    
    private static boolean isDateValid(String dateStr) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            Date inputDate = sdf.parse(dateStr);
            Date currentDate = new Date();
            
            Calendar inputCal = Calendar.getInstance();
            inputCal.setTime(inputDate);
            inputCal.set(Calendar.HOUR_OF_DAY, 0);
            inputCal.set(Calendar.MINUTE, 0);
            inputCal.set(Calendar.SECOND, 0);
            inputCal.set(Calendar.MILLISECOND, 0);
            
            Calendar currentCal = Calendar.getInstance();
            currentCal.setTime(currentDate);
            currentCal.set(Calendar.HOUR_OF_DAY, 0);
            currentCal.set(Calendar.MINUTE, 0);
            currentCal.set(Calendar.SECOND, 0);
            currentCal.set(Calendar.MILLISECOND, 0);
            
            return !inputCal.getTime().before(currentCal.getTime());
        } catch (Exception e) {
            return false;
        }
    }
    
    public static int getValidAge(Scanner scanner, String prompt) {
        return getValidInt(scanner, 1, 120, prompt);
    }
    
    public static String getValidGender(Scanner scanner, String prompt) {
        String gender;
        do {
            System.out.print(prompt + " (M/F): ");
            gender = scanner.nextLine().trim().toUpperCase();
            if (!gender.equals("M") && !gender.equals("F")) {
                System.out.println("Invalid gender. Please enter 'M' or 'F'.");
            }
        } while (!gender.equals("M") && !gender.equals("F"));
        
        if (gender.equals("M")) {
            return "Male";
        } else {
            return "Female";
        }
    }
    
    public static int getValidId(Scanner scanner, String prompt) {
        int id;
        do {
            id = getValidInt(scanner, prompt);
            if (id <= 0) {
                System.out.println("ID must be a positive number.");
            }
        } while (id <= 0);
        return id;
    }
    
    public static double getValidPrice(Scanner scanner, String prompt) {
        double price;
        do {
            price = getValidDouble(scanner, prompt);
            if (price < 0) {
                System.out.println("Price cannot be negative.");
            }
        } while (price < 0);
        return price;
    }
    
    public static int getValidQuantity(Scanner scanner, String prompt) {
        int quantity;
        do {
            quantity = getValidInt(scanner, prompt);
            if (quantity <= 0) {
                System.out.println("Quantity must be a positive number.");
            }
        } while (quantity <= 0);
        return quantity;
    }
} 