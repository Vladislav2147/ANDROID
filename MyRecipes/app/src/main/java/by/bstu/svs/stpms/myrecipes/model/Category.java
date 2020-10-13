package by.bstu.svs.stpms.myrecipes.model;

import androidx.annotation.NonNull;

public enum Category {
    BAKING ("Baking"), MAIN_COURSE ("Main Course"), MEAT ("Meat"),
    SALAD ("Salad"), SANDWICH ("Sandwich"), SOUP ("Soup");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public static Category getEnumFromString (String name) {

        for (Category category: values()) {
            if (category.getName().equalsIgnoreCase(name)) return category;
        }
        throw new IllegalArgumentException("Category with name " + name + " not found");

    }
}
