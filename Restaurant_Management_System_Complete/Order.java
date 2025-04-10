import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.io.IOException;
import java.nio.file.*;

public class Order {
    private final Customer_record customer;
    private final List<MenuItem> items;
    private final Date orderDate;

    public Order(Customer_record customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
        this.orderDate = new Date();
    }

    public void addItem(MenuItem item) throws InvalidMenuItemException {
        if (item == null) {
            throw new InvalidMenuItemException("Menu item cannot be null!");
        }
        items.add(item);
    }

    public List<MenuItem> getItems() {
        return new ArrayList<>(items);
    }

    public double calculateTotal() {
        return Math.round(items.stream().mapToDouble(MenuItem::getPrice).sum() * 100.0) / 100.0;
    }

    public void applyDiscount(Function<MenuItem, Double> discountPercentage) {
        for (var i = 0; i < items.size(); i++) {
            var item = items.get(i);
            var discountedPrice = DiscountManager.applyDiscount(item, discountPercentage);
            if (item instanceof FoodItem) {
                items.set(i, new FoodItem(item.getName(), discountedPrice, item.getCategory()));
            } else if (item instanceof BeverageItem b) {
                items.set(i, new BeverageItem(item.getName(), discountedPrice, b.isAlcoholic()));
            } else if (item instanceof SpecialFoodItem) {
                items.set(i, new SpecialFoodItem(item.getName(), discountedPrice, item.getCategory()));
            }
        }
    }

    public void logOrder() {
        try {
            String logEntry = "Order for: " + customer.name() + " at " + orderDate + "\n";
            Files.writeString(Path.of("order_log.txt"), logEntry, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Failed to log order: " + e.getMessage());
        }
    }

    public void prepare() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Callable<Void>> tasks = items.stream().map(item -> (Callable<Void>) () -> {
            System.out.println(item.getName() + " is being prepared...");
            Thread.sleep(1000);
            System.out.println(item.getName() + " is ready!\n");
            return null;
        }).toList();

        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            System.out.println("Preparation interrupted.");
        } finally {
            executor.shutdown();
        }
    }



    @Override
    public String toString() {
        var orderDetails = new StringBuilder("Order for " + customer + "\nItems:\n");
        for (var i = 0; i < items.size(); i++) {
            orderDetails.append(i + 1).append(". ").append(items.get(i)).append("\n");
        }
        orderDetails.append("Total: €").append(calculateTotal()).append("\nDate: ").append(orderDate);
        return orderDetails.toString();
    }
}
