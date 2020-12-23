package by.bstu.vs.stpms.lablistsqlite.model.dao;

import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.model.entity.Subject;

public interface SubjectDao extends Dao<Subject> {
    List<Subject> getAllByTermId(int id);
}
