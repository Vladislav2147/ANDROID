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

    @Query("SELECT * FROM lab WHERE name == :name")
    Lab getByName(String name);

    @Delete
    void delete(Lab lab);

    @Update
    void update(Lab lab);
}
