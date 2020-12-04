package by.bstu.vs.stpms.lablist.model.entity;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import by.bstu.vs.stpms.lablist.BR;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static androidx.room.ForeignKey.CASCADE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "lab", foreignKeys = @ForeignKey(
        entity = Subject.class,
        parentColumns = "id",
        childColumns = "subject_id",
        onUpdate = CASCADE, onDelete = CASCADE
))
public class Lab extends BaseObservable implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    @ColumnInfo(name = "subject_id")
    private int subjectId;
    @ColumnInfo(name = "task_file_path")
    private String taskFilePath;
    @ColumnInfo(name = "code_reference")
    private String codeReference;
    private String notes;
    @ColumnInfo(name = "is_passed")
    private boolean isPassed;

    @Bindable
    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
        notifyPropertyChanged(BR.passed);
    }
}
