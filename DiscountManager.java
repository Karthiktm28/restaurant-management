import java.util.function.Function;

public class DiscountManager {
    public static double applyDiscount(MenuItem item, Function<MenuItem, Double> discountFunction) {
        return discountFunction.apply(item);
    }

    public static double applyFlatDiscount(MenuItem item, double amount) {
        return Math.max(0, item.getPrice() - amount);
    }

    public static double applyPercentageDiscount(MenuItem item, double percentage) {
        return item.getPrice() * (1 - percentage / 100.0);
    }
}
