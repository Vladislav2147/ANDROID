package by.bstu.vs.stpms.lablist.model.entity;

import androidx.room.Entity;
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
@Entity(tableName = "term")
public class Term {

    @PrimaryKey
    private int id;
    private int course;
    private int semester;

    @NotNull
    @Override
    public String toString() {
        return course + " course " + semester + "semester";
    }

}
