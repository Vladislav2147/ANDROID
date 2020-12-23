package by.bstu.vs.stpms.lablistsqlite.ui.term;

import android.app.Application;

import by.bstu.vs.stpms.lablistsqlite.model.entity.Term;
import by.bstu.vs.stpms.lablistsqlite.model.repository.impl.TermRepository;
import by.bstu.vs.stpms.lablistsqlite.ui.AbstractCrudViewModel;

public class TermViewModel extends AbstractCrudViewModel<Term, TermRepository> {

    public TermViewModel(Application application) {
        super(application);
        repository = new TermRepository(application);
        listLiveData = repository.getTerms();
    }
}