package by.bstu.vs.stpms.lablistsqlite.model.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import java.io.Serializable;

import by.bstu.vs.stpms.lablistsqlite.BR;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lab extends BaseObservable implements Serializable, Entity {

    private Integer id;
    private String name;
    private int subjectId;
    private String taskFilePath;
    private String codeReference;
    private String notes;
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
