package by.bstu.vs.stpms.lablistsqlite.model.entity;

import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Term implements Entity {

    private Integer id;
    private int course;
    private int semester;

    @NotNull
    @Override
    public String toString() {
        return course + " Course " + semester + " Semester";
    }

}
