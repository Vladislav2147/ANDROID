package by.bstu.vs.stpms.lablistsqlite.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.model.entity.Subject;

public interface SubjectDao extends Dao<Subject> {
    List<Subject> getAllByTermId(int id);
}
