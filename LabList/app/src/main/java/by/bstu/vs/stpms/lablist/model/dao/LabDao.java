package by.bstu.vs.stpms.lablist.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import by.bstu.vs.stpms.lablist.model.entity.Lab;

@Dao
public interface LabDao {
    @Insert
    void insert(Lab lab);

    @Query("SELECT * FROM lab")
    List<Lab> getAll();

    @Query("SELECT * FROM lab WHERE id == :id")
    Lab getById(int id);

    @Delete
    void delete(Lab lab);

    @Update
    void update(Lab lab);

    @Query("SELECT * FROM lab WHERE subject_id == :subjectId")
    List<Lab> getLabsBySubjectId(int subjectId);

    @Query("SELECT * FROM lab WHERE subject_id == :subjectId AND is_passed == :isPassed")
    List<Lab> getLabsByStateAndSubjectId(boolean isPassed, int subjectId);

}
