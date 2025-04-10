import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
public class RestaurantApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Order order = null;

        // Language Selection
        System.out.println("=========================================");
        System.out.println("🍽️  Welcome to the Restaurant Management System!");
        System.out.println("=========================================\n");

        System.out.println("Select Language / Seleccione el idioma / ಭಾಷೆ ಆಯ್ಕೆಮಾಡಿ:");
        System.out.println("1. English");
        System.out.println("2. Español");
        System.out.println("3. ಕನ್ನಡ");
        System.out.print("Enter choice: ");
        int langChoice = scanner.nextInt();
        scanner.nextLine(); // clear buffer

        Locale locale;
        if (langChoice == 2) {
            locale = new Locale("es", "ES");
        } else if (langChoice == 3) {
            locale = new Locale("kn", "IN");
        } else {
            locale = new Locale("en", "US");
        }

        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        System.out.println("\n" + bundle.getString("welcome"));

        // Rest of your app logic continues exactly the same...
        // (no changes needed to the rest of the code you shared)


        // Load menu
        List<MenuItem> menu = new ArrayList<>(List.of(
        	    new SpecialFoodItem(bundle.getString("item.biryani"), 12.99, MenuItem.Category.FOOD),
        	    new SpecialFoodItem(bundle.getString("item.kebab"), 8.49, MenuItem.Category.FOOD),
        	    new BeverageItem(bundle.getString("item.cola"), 2.99, false),
        	    new BeverageItem(bundle.getString("item.beer"), 5.99, true),
        	    new FoodItem(bundle.getString("item.veg_burger"), 6.99, MenuItem.Category.FOOD),
        	    new FoodItem(bundle.getString("item.chicken_wrap"), 7.99, MenuItem.Category.FOOD)
        	));

        while (true) {
            System.out.println("\n" + bundle.getString("menu.option"));
            System.out.println("1. " + bundle.getString("menu.create"));
            System.out.println("2. " + bundle.getString("menu.show"));
            System.out.println("3. " + bundle.getString("menu.add"));
            System.out.println("4. " + bundle.getString("menu.view"));
            System.out.println("5. " + bundle.getString("menu.log"));
            System.out.println("6. " + bundle.getString("menu.prepare"));
            System.out.println("7. " + bundle.getString("menu.discount"));
            System.out.println("8. " + bundle.getString("menu.exit"));
            System.out.print(bundle.getString("enter.choice"));

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> {
                    if (order == null) {
                        System.out.print(bundle.getString("enter.customer.name"));
                        String name = scanner.nextLine();
                        System.out.print(bundle.getString("enter.table.number"));
                        int tableNumber = scanner.nextInt();
                        scanner.nextLine();
                        Customer_record customer = new Customer_record(name, tableNumber);
                        order = new Order(customer);
                        System.out.println(bundle.getString("order.created"));
                    } else {
                        System.out.println(bundle.getString("order.already"));
                    }
                }

                case 2 -> {
                    menu.sort(Comparator.comparing(MenuItem::getPrice)); // Sort by price
                    System.out.println("\n" + bundle.getString("menu.header"));
                    for (int i = 0; i < menu.size(); i++) {
                        System.out.println((i + 1) + ". " + menu.get(i));
                    }
                }


                case 3 -> {
                    if (order == null) {
                        System.out.println(bundle.getString("no.order"));
                        break;
                    }
                    System.out.println(bundle.getString("enter.item.numbers"));
                    String[] selections = scanner.nextLine().split(",");
                    for (String sel : selections) {
                        try {
                            int index = Integer.parseInt(sel.trim()) - 1;
                            if (index >= 0 && index < menu.size()) {
                                order.addItem(menu.get(index));
                                System.out.println(bundle.getString("added") + " " + menu.get(index).getName());
                            } else {
                                System.out.println(bundle.getString("invalid.item") + " " + (index + 1));
                            }
                        } catch (Exception e) {
                            System.out.println(bundle.getString("error.processing") + " " + sel.trim());
                        }
                    }
                }

                case 4 -> {
                    if (order != null) {
                        System.out.println(order);
                        System.out.println(order); // Show existing order details

                     // Count of items
                     System.out.println("Total items in the order: " + order.getItems().stream().count());

                     // Grouping by category
                     Map<MenuItem.Category, List<MenuItem>> groupedItems = order.getItems()
                         .stream()
                         .collect(Collectors.groupingBy(MenuItem::getCategory));

                     System.out.println("Grouped by category:");
                     groupedItems.forEach((category, items) -> {
                         System.out.println("- " + category + ":");
                         items.forEach(item -> System.out.println("  • " + item.getName()));
                     });
                     
                    } else {
                        System.out.println(bundle.getString("no.order"));
                    }
                }

                case 5 -> {
                    if (order != null) {
                        order.logOrder();
                        System.out.println(bundle.getString("order.logged"));
                    } else {
                        System.out.println(bundle.getString("no.order"));
                    }
                }

                case 6 -> {
                    if (order != null) {
                        try {
                            order.prepare();
                        } catch (InterruptedException e) {
                            System.out.println(bundle.getString("preparation.interrupted"));
                        }
                    } else {
                        System.out.println(bundle.getString("no.order"));
                    }
                }

                case 7 -> {
                    if (order != null) {
                        System.out.print(bundle.getString("enter.discount"));
                        double discountPercentage = scanner.nextDouble();
                        scanner.nextLine();

                        double originalTotal = order.calculateTotal();
                        Function<MenuItem, Double> discountFunc =
                                item -> item.getPrice() * (1 - discountPercentage / 100.0);

                        order.applyDiscount(discountFunc);
                        double newTotal = order.calculateTotal();
                        double savings = originalTotal - newTotal;

                        System.out.printf("%s\n%s €%.2f\n%s €%.2f\n",
                                bundle.getString("discount.applied"),
                                bundle.getString("original.total"), originalTotal,
                                bundle.getString("new.total"), newTotal);
                        System.out.printf("%s €%.2f!\n", bundle.getString("you.saved"), savings);
                    } else {
                        System.out.println(bundle.getString("no.order"));
                    }
                }

                case 8 -> {
                    System.out.println(bundle.getString("exit.message"));
                    return;
                }

                default -> {
                    int _ = choice; // Java 22 feature: unused pattern
                    System.out.println(bundle.getString("invalid.choice"));
                }
            }

        }
    }
}
