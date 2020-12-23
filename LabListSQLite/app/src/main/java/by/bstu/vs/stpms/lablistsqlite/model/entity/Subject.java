package by.bstu.vs.stpms.lablistsqlite.model.entity;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject implements Entity {

    private Integer id;
    @NonNull
    private String name;
    private int termId;

}
