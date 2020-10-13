package by.bstu.svs.stpms.myrecipes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Recipe {

    private Long id;
    private String title;

    public Recipe(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    private Category category;
    private String ingredients;
    private String steps;
    private String picture;
    private Time timeToCook;

}
