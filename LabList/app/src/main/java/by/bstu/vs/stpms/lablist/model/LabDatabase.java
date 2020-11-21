package by.bstu.vs.stpms.lablist.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import by.bstu.vs.stpms.lablist.model.dao.LabDao;
import by.bstu.vs.stpms.lablist.model.dao.SubjectDao;
import by.bstu.vs.stpms.lablist.model.dao.TermDao;
import by.bstu.vs.stpms.lablist.model.entity.Lab;
import by.bstu.vs.stpms.lablist.model.entity.Subject;
import by.bstu.vs.stpms.lablist.model.entity.Term;

@Database(entities = {Lab.class, Subject.class, Term.class}, version = 3)
public abstract class LabDatabase extends RoomDatabase {
    public abstract LabDao getLabDao();
    public abstract SubjectDao getSubjectDao();
    public abstract TermDao getTermDao();
}
