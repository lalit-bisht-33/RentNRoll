/*
class User:
    - Represents a user with a username and role (admin or customer).
    - Methods: getUsername(), getRole(), isAdmin()

 * class Car:
    - Represents a car with auto-generated ID, brand, model, price, image path.
     - Methods:
        - getCarId(), getBrand(), getModel(), getBasePrice()
        - getImagePath(), isAvailable(), rent(), returnCar()
        - calculatePrice(days): total rental cost

 * class Customer:
    - Represents a customer with ID and name.
    - Methods: getCustomerId(), getName()

 * class Rental:
    - Represents a rental record with car, customer, and days rented.
    - Methods: getCar(), getCustomer(), getDays()

 * class CarRentalSystem:
    - Manages cars, customers, rentals.
    - UserRole(): login and route to admin/customer
    - adminMenu(): add/delete cars (admin only)
    - customerMenu(): rent/return cars, preview car image
    - openCarImage(imagePath): opens image in 2 sec delay using Desktop
    - generateReceipt(...): saves receipt in `/User_List/` folder

 * class FeedbackUtils:
    - Static utility to save feedbacks in CSV format inside `/Feedbacks/` folder.
    - Method: saveFeedbackToCSV(name, email, experience, suggestion, rating, recommend)

*/




import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.awt.Desktop;
import java.text.SimpleDateFormat;

class User {
    private String username;
    private String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role.toLowerCase();
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public boolean isAdmin() {
        return role.equals("admin");
    }
}


class Car {
    private static int idCounter = 1;
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private String imagePath;
    private boolean isAvailable;

    public Car( String brand, String model, double basePricePerDay,String imagePath) {
        this.carId = generateCarId();
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.imagePath = imagePath;
        this.isAvailable = true;
    }
    private String generateCarId() {
        return String.format("C%03d", idCounter++);
    }
    public String getCarId() {
        return carId;
    }

    public double getBasePrice() {
        return basePricePerDay;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    private static int idCounter = 1;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    private void generateReceipt(String name, String mobile, String email,String carBrand, String carModel, int days, double totalPrice) {
        try {
            File folder = new File("User_List");
            if (!folder.exists()) folder.mkdir();

            String fileName = name.trim().replaceAll("\\s+", "_") + ".txt";
            FileWriter fw = new FileWriter("User_List/" + fileName);
            fw.write("====== Car Rental Receipt ======\n");
            fw.write("\n");

            fw.write("=================\n");
            fw.write("Customer Detail:\n");
            fw.write("=================\n");
            fw.write("Name: " + name + "\n");
            fw.write("Mobile: " + mobile + "\n");
            fw.write("Email: " + email + "\n");
            fw.write("\n");

            fw.write("=================\n");
            fw.write("Order Detail:\n");
            fw.write("=================\n");
            fw.write("Car Rented    : " + carBrand + " " + carModel + "\n");
            fw.write("Rental Days   : " + days + "\n");
            fw.write(String.format("Total Amount  : ₹%.2f%n", totalPrice));
            fw.write("Timestamp: " + java.time.LocalDateTime.now() + "\n");
            fw.write("------------------------------\n");
            fw.close();
            System.out.println("Customer details saved to User_List ");
        } catch (IOException e) {
            System.out.println("Could not save customer details: " + e.getMessage());
        }
    }

    public static void openCarImage(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    System.out.println("Opening image preview in 2 seconds...");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Delay interrupted: " + e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                    Desktop.getDesktop().open(imageFile);

                } else {
                    System.out.println("Desktop not supported on this platform.");
                }
            } else {
                System.out.println("Image file not found: " + imagePath);
            }
        } catch (IOException e) {
            System.out.println("Failed to open image: " + e.getMessage());
        }
    }



    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));

        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);

        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void UserRole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Login as:\n1. Admin\n2. Customer");
        System.out.print("Enter your choice: ");
        int loginType = scanner.nextInt();
        scanner.nextLine();

        String adminName="admin";
        String password="password";
        if (loginType == 1) {
            System.out.print("Enter admin username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String code=scanner.nextLine();
            if(Objects.equals(username, adminName) && Objects.equals(code,password)){
                adminMenu();
            }
        } else {
            customerMenu();
        }
    }


    public void adminMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. Add a Car");
            System.out.println("2. Delete a Car");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter brand: ");
                String brand = scanner.nextLine();
                System.out.print("Enter model: ");
                String model = scanner.nextLine();
                System.out.print("Enter base price per day: ");
                double basePricePerDay = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Enter image path: ");
                String imagePath = scanner.nextLine();

                String carId = String.format("C%03d", idCounter++);
                Car car = new Car(brand, model, basePricePerDay,imagePath);
                addCar(car);
                System.out.println("Car added with ID: " + carId);
            }
            else if (choice == 2) {
                System.out.print("Enter Car ID to delete: ");
                String id = scanner.nextLine();
                boolean removed = cars.removeIf(car -> car.getCarId().equalsIgnoreCase(id));

                if (removed) {
                    System.out.println("Car deleted.");
                } else {
                    System.out.println("Car not found.");
                }
            }
            else if (choice == 3) {
                UserRole();
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }


    public void customerMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Preview cars");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("\n== Rent a Car ==\n");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();
                System.out.print("Enter your mobile number: ");
                String mobile = scanner.nextLine();
                System.out.print("Enter your email: ");
                String email = scanner.nextLine();

                System.out.println("\nAvailable Cars:");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel()+ "- ₹"+
                                car.getBasePrice());
                    }
                }

                System.out.print("\nEnter the car ID you want to rent: ");
                String carId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {


                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: ₹%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                        generateReceipt(customerName, mobile, email, selectedCar.getBrand(), selectedCar.getModel(), rentalDays, totalPrice);
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                String carId = scanner.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equalsIgnoreCase(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());

                        System.out.print("\nWould you like to give feedback? (Y/N): ");
                        String feedbackChoice = scanner.nextLine();

                        if (feedbackChoice.equalsIgnoreCase("Y")) {
                            System.out.println("\n====== Feedback Form ======");

                            System.out.print("Enter your email: ");
                            String customerEmail = scanner.nextLine();

                            System.out.print("How was your rental experience?\n> ");
                            String experience = scanner.nextLine();

                            System.out.print("Any suggestions for improvement?\n> ");
                            String suggestion = scanner.nextLine();

                            System.out.print("Rate us (1 to 5): ");
                            String rating = scanner.nextLine();

                            System.out.print("Would you recommend us? (Yes/No): ");
                            String recommend = scanner.nextLine();


                            FeedbackUtils.saveFeedbackToCSV(customer.getName(), customerEmail, experience, suggestion, rating, recommend);
                        }
                        else {
                            System.out.println("Thank you for choosing our service!!!");
                        }

                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            } else if (choice == 3) {
                System.out.println("Enter carId to preview car");
                System.out.println("\nAvailable Cars:");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel() + "- ₹"+
                        car.getBasePrice());
                    }
                }

                System.out.print("\nEnter the car ID you want to rent: ");
                String carId = scanner.nextLine();

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equalsIgnoreCase(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar != null) {
                    openCarImage(selectedCar.getImagePath());
                }
            }
            else if (choice == 4) {
                break;
            }
            else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Car Rental System!");
    }

}

 class FeedbackUtils {

    public static void saveFeedbackToCSV(String name, String email, String experience, String suggestion, String rating, String recommend) {
        try {
            File folder = new File("Feedbacks");
            if (!folder.exists()) folder.mkdir();

            File csvFile = new File("Feedbacks/feedback_logs.csv");
            boolean fileExists = csvFile.exists();

            FileWriter writer = new FileWriter(csvFile, true);


            if (!fileExists) {
                writer.write("Customer Name,Customer Email,Date,Experience,Suggestions,Rating,Recommend\n");
            }

            String date = new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(new Date());

            writer.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                    name, email, date, experience, suggestion, rating, recommend));

            writer.close();
            System.out.println("Feedback saved to: Feedbacks/feedback_log.csv");
        } catch (IOException e) {
            System.out.println("Could not save feedback to CSV: " + e.getMessage());
        }
    }
}

public class Main{
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("Toyota", "Camry", 8000.0,"Images/camry.jpg");
        Car car2 = new Car( "Honda", "Accord", 5000.0,"Images/honda.jpg");
        Car car3 = new Car( "Mahindra", "Thar", 15000.0,"Images/thar.jpg");
        Car car4 = new Car( "Tesla", "ModelS", 20000.0,"Images/thar.jpg");

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);

        rentalSystem.UserRole();
    }
}