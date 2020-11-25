package by.bstu.vs.stpms.lablist.ui.term;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import by.bstu.vs.stpms.lablist.R;

public class TermFragment extends Fragment {

    private TermViewModel termViewModel;
    private RecyclerView recyclerView;
    private TermAdapter termAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        termViewModel =
                new ViewModelProvider(this).get(TermViewModel.class);
        View root = inflater.inflate(R.layout.fragment_term, container, false);

        termAdapter = new TermAdapter();
        termViewModel.getTerms().observe(getViewLifecycleOwner(), terms -> {
            termAdapter.setTerms(terms);
        });
        recyclerView = root.findViewById(R.id.rv_term);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(termAdapter);

        return root;
    }
}