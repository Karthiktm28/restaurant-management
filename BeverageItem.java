public final class BeverageItem extends MenuItem {
    private final boolean isAlcoholic;

    public BeverageItem(String name, double price, boolean isAlcoholic) {
        super(name, price, Category.BEVERAGE);
        this.isAlcoholic = isAlcoholic;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    @Override
    public String toString() {
        return super.toString() + ", Alcoholic: " + isAlcoholic;
    }
}
