public final class SpecialFoodItem extends MenuItem {
    public SpecialFoodItem(String name, double price) {
        super(name, price, Category.FOOD);
    }

    public SpecialFoodItem(String name, double price, Category category) {
        super(name, price, category);
    }

    @Override
    public String toString() {
        return super.toString() + " (Special)";
    }
}
