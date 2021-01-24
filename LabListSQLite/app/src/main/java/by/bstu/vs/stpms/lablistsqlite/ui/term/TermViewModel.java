package by.bstu.vs.stpms.lablistsqlite.ui.term;

import javax.inject.Inject;

import by.bstu.vs.stpms.lablistsqlite.model.entity.Term;
import by.bstu.vs.stpms.lablistsqlite.model.repository.impl.TermRepository;
import by.bstu.vs.stpms.lablistsqlite.ui.AbstractCrudViewModel;

public class TermViewModel extends AbstractCrudViewModel<Term, TermRepository> {

    @Inject
    public TermViewModel(TermRepository repository) {
        super(repository);
        listLiveData = repository.getTerms();
    }
}