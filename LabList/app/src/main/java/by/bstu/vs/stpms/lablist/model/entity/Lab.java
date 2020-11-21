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
    private int id;
    @NonNull
    private String name;
    @ForeignKey(
            entity = Subject.class,
            parentColumns = "id",
            childColumns = "subject_id",
            onUpdate = CASCADE, onDelete = CASCADE
    )
    @NonNull
    @ColumnInfo(name = "subject_id")
    private String subjectId;
    @ColumnInfo(name = "task_file_path")
    private String taskFilePath;
    @ColumnInfo(name = "code_reference")
    private String codeReference;
    private String screenshot;
    private Integer mark;
    @ColumnInfo(name = "is_passed")
    private boolean isPassed;

}
