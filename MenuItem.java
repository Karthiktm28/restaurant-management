public sealed abstract class MenuItem permits FoodItem, BeverageItem, SpecialFoodItem {
    protected String name;
    protected double price;
    protected Category category;

    public MenuItem(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    @Override
    public String toString() {
        return "Item: " + name + ", Price: €" + String.format("%.2f", price) + ", Category: " + category;
    }

    public enum Category { FOOD, BEVERAGE }
}
