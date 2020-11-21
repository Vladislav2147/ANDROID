package by.bstu.vs.stpms.lablist.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import by.bstu.vs.stpms.lablist.model.dao.SubjectDao;
import by.bstu.vs.stpms.lablist.model.entity.Subject;

@Database(entities = {Subject.class}, version = 2)
public abstract class LabDatabase extends RoomDatabase {
    public abstract SubjectDao getSubjectDao();
}
