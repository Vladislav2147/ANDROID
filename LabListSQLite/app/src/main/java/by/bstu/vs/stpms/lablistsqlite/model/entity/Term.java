package by.bstu.vs.stpms.lablistsqlite.model.entity;

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

    public String format() {
        return course + " Course " + semester + " Semester";
    }

    @Override
    public String toString() {
        return "Term{" +
                "id=" + id +
                ", course=" + course +
                ", semester=" + semester +
                '}';
    }
}
