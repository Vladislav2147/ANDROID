package by.bstu.vs.stpms.lablist.model.entity;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import by.bstu.vs.stpms.lablist.BR;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import static androidx.room.ForeignKey.CASCADE;

@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "lab")
public class Lab extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(@NonNull String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTaskFilePath() {
        return taskFilePath;
    }

    public void setTaskFilePath(String taskFilePath) {
        this.taskFilePath = taskFilePath;
    }

    public String getCodeReference() {
        return codeReference;
    }

    public void setCodeReference(String codeReference) {
        this.codeReference = codeReference;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    @Bindable
    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
        notifyPropertyChanged(BR.passed);
    }
}
