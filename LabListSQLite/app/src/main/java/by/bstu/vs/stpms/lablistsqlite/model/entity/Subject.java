package by.bstu.vs.stpms.lablistsqlite.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
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
public class Subject implements Entity {

    private Integer id;
    @NonNull
    private String name;
    private int termId;

}
