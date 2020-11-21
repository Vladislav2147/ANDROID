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
@Entity(tableName = "lab")
public class Lab {

    @PrimaryKey
    private String name;
    @ForeignKey(
            entity = Subject.class,
            parentColumns = "name",
            childColumns = "subject_name",
            onUpdate = CASCADE, onDelete = CASCADE
    )
    @NonNull
    private String subject_name;
    @ColumnInfo(name = "task_reference")
    private String taskReference;
    @ColumnInfo(name = "code_reference")
    private String codeReference;
    private String screenshot;
    private Integer mark;
    @ColumnInfo(name = "is_passed")
    private boolean isPassed;

}
