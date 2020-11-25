package by.bstu.vs.stpms.lablist.ui.term;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TermViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TermViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}