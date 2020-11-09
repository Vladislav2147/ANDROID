package by.bstu.svs.stpms.myrecipes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Query {
    private String selection;
    private String[] selectionArgs;
    private String orderBy;
}
