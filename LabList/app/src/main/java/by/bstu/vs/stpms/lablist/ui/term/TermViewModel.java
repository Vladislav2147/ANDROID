package by.bstu.vs.stpms.lablist.ui.term;

import android.app.Application;

import by.bstu.vs.stpms.lablist.model.entity.Term;
import by.bstu.vs.stpms.lablist.model.repository.TermRepository;
import by.bstu.vs.stpms.lablist.ui.AbstractCrudViewModel;

public class TermViewModel extends AbstractCrudViewModel<Term, TermRepository> {

    public TermViewModel(Application application) {
        super(application);
        repository = new TermRepository(application);
        listLiveData = repository.getTerms();
    }
}