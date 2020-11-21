package by.bstu.vs.stpms.lablist.model.entity;

import androidx.room.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "term", primaryKeys = {"course", "semester"})
public class Term {

    private int course;
    private int semester;

    @Override
    public String toString() {
        return course + " course " + semester + "semester";
    }

}
