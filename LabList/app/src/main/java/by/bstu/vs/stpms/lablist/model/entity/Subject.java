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
@Entity(tableName = "subject", foreignKeys = @ForeignKey(
        entity = Term.class,
        parentColumns = "id",
        childColumns = "term_id",
        onUpdate = CASCADE, onDelete = CASCADE
))
public class Subject {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @NonNull
    private String name;
    @ColumnInfo(name = "term_id")
    private int termId;

}
