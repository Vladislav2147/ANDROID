package by.bstu.vs.stpms.lablist.ui.term;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

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
}