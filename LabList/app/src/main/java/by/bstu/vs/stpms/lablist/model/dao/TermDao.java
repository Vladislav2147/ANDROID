package by.bstu.vs.stpms.lablist.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import by.bstu.vs.stpms.lablist.model.entity.Term;

@Dao
public interface TermDao {
    @Insert
    void insert(Term term);

    @Query("SELECT * FROM term ORDER BY course DESC, semester DESC")
    LiveData<List<Term>> getAll();

    @Query("SELECT * FROM term WHERE id == :id")
    LiveData<Term> getById(int id);

    @Delete
    void delete(Term term);

    @Update
    void update(Term term);
}
