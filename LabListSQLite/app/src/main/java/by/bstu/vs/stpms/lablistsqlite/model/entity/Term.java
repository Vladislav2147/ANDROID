package by.bstu.vs.stpms.lablistsqlite.model.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "term",
        indices = {@Index(value = {"course", "semester"},
        unique = true)})
public class Term {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private int course;
    private int semester;

    @NotNull
    @Override
    public String toString() {
        return course + " Course " + semester + " Semester";
    }

}
