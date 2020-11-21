package by.bstu.vs.stpms.lablist.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static androidx.room.ForeignKey.CASCADE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "subject")
@ForeignKey(
        entity = Term.class,
        parentColumns = {"course", "semester"},
        childColumns = {"course_id", "semester_id"},
        onUpdate = CASCADE, onDelete = CASCADE)
public class Subject {

    @NonNull
    @PrimaryKey
    private String name;
    @ColumnInfo(name = "course_id")
    private int courseId;
    @ColumnInfo(name = "semester_id")
    private int semesterId;


}
