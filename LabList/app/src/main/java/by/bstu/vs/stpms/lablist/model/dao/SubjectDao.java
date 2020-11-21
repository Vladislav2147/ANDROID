package by.bstu.vs.stpms.lablist.model.dao;

import androidx.lifecycle.LiveData;
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

    @Query("SELECT * FROM subject WHERE name == :name")
    LiveData<Subject> getByName(String name);

    @Delete
    void delete(Subject subject);

    @Update
    void update(Subject subject);

    @Query("SELECT * FROM subject WHERE term_id == :id")
    LiveData<List<Subject>> getAllByTermId(int id);
}
