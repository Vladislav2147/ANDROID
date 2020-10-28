package by.bstu.svs.stpms.myrecipes.model;

public enum Category {
    BAKING("Baking"), MAIN_COURSE("Main Course"), MEAT("Meat"),
    OTHER("Other"), SALAD("Salad"), SANDWICH("Sandwich"), SOUP("Soup");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Category getEnumFromString(String name) {

        for (Category category : values()) {
            if (category.toString().equalsIgnoreCase(name)) return category;
        }
        throw new IllegalArgumentException("Category with name " + name + " not found");

    }
}
