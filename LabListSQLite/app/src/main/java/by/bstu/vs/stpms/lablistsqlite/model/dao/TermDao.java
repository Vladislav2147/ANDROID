package by.bstu.vs.stpms.lablistsqlite.model.dao;
import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.model.entity.Term;

public interface TermDao extends Dao<Term> {
    List<Term> getAll();
}
