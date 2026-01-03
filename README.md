# Restaurant Management System

A multilingual, command-line based Restaurant Management System built in Java using modern features like sealed classes, records, streams, lambdas, and localization. This project was developed for the OOP2 module to simulate real-world restaurant order processing and management.

---

## Features

- **Create and manage orders** with customer details
- **Add multiple menu items** to an order
- **Apply discounts** using lambda functions
- **Prepare food items** concurrently using ExecutorService
- **View order summary**, total, and category-wise breakdown
- **Log orders** to file using Java NIO2
- **Multilingual support** (English, Spanish, Kannada) using ResourceBundle
- **Advanced Java 22+ Features:** Sealed classes, pattern matching, modern switch expressions

---

## Java Concepts Used

- Object-Oriented Programming (Inheritance, Encapsulation, Polymorphism)
- Sealed Classes
- Java Records
- Streams and Collectors
- Functional Interfaces and Lambdas
- Concurrency with ExecutorService
- Java NIO2 for file handling
- Localization with ResourceBundle
- Exception handling and custom exceptions

---

## How to Run

1. Compile all Java files:
```bash
javac *.java
```

2. Run the main class:
```bash
java RestaurantApp
```

3. Follow the on-screen instructions to select language, create orders, and use other features.

---

## Folder Structure
```
/RestaurantManagementSystem
├── RestaurantApp.java
├── MenuItem.java
├── FoodItem.java
├── BeverageItem.java
├── SpecialFoodItem.java
├── Order.java
├── DiscountManager.java
├── Customer_record.java
├── InvalidMenuItemException.java
├── messages_en.properties
├── messages_es.properties
├── messages_kn.properties
└── order_log.txt (auto-generated)
```

---

## GitHub
This project is maintained on GitHub:
[https://github.com/Karthiktm28/restaurant-management]

---

## Author
**[Karthik Thyloor Mahesh]**  
OOP2 Java Assignment  

