package by.bstu.svs.stpms.myrecipes.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Recipe implements Serializable {

    private Long id;
    private String title;
    private Category category;
    private String ingredients;
    private String steps;
    private String picture;
    private Time timeToCook;

    public Recipe(Long id, String title) {
        this.id = id;
        this.title = title;
        this.category = Category.OTHER;
    }

    public void setCategory(String category) {
        this.category = Category.getEnumFromString(category);
    }

    @Exclude
    public void setCategory(Category category) {
        this.category = category;
    }
}
