package by.bstu.vs.stpms.lablistsqlite.model.dao;

import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.model.entity.Lab;

public interface LabDao extends Dao<Lab> {
    Lab getById(int id);
    List<Lab> getLabsBySubjectId(int subjectId);
    List<Lab> getLabsByStateAndSubjectId(boolean isPassed, int subjectId);
}
