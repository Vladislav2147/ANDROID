package by.bstu.vs.stpms.lablist.ui.term;

import android.app.Application;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.function.Consumer;

import by.bstu.vs.stpms.lablist.model.entity.Term;
import by.bstu.vs.stpms.lablist.model.repository.TermRepository;

public class TermViewModel extends AndroidViewModel {

    private LiveData<List<Term>> termsLiveData;
    private TermRepository repository;

    public TermViewModel(Application application) {
        super(application);
        repository = new TermRepository(application);
        termsLiveData = repository.getTerms();
    }

    public LiveData<List<Term>> getTerms() {
        return termsLiveData;
    }

    public LiveData<Term> getTermById(int id) {
        return repository.getTermById(id);
    }

    public void addTerm(Term term, Consumer<SQLiteException> onError) {
        repository.insertTerm(term, onError);
    }

    public void updateTerm(Term term, Consumer<SQLiteException> onError) {
        repository.updateTerm(term, onError);
    }

    public void deleteTerm(Term term, Consumer<SQLiteException> onError) {
        repository.deleteTerm(term, onError);
    }
}