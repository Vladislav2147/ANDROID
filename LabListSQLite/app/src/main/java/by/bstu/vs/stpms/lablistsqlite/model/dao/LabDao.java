package by.bstu.vs.stpms.lablistsqlite.model.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.model.entity.Lab;

public interface LabDao extends Dao<Lab> {
    @Override
    void insert(Lab lab);
    @Override
    void delete(Lab lab);
    @Override
    void update(Lab lab);
    @Override
    Lab getByCursor(Cursor cursor);

    Lab getById(int id);
    List<Lab> getLabsBySubjectId(int subjectId);
    List<Lab> getLabsByStateAndSubjectId(boolean isPassed, int subjectId);
}
