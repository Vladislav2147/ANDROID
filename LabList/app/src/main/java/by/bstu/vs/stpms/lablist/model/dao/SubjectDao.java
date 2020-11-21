package by.bstu.vs.stpms.lablist.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import by.bstu.vs.stpms.lablist.model.entity.Subject;

@Dao
public interface SubjectDao {
    @Insert
    void insert(Subject subject);

    @Query("SELECT * FROM subject")
    List<Subject> getAll();

    @Delete
    void delete(Subject subject);

    @Update
    void update(Subject subject);
}
