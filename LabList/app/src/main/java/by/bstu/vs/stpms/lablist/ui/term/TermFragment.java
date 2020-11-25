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

import java.util.ArrayList;

import by.bstu.vs.stpms.lablist.R;
import by.bstu.vs.stpms.lablist.model.entity.Term;
import by.bstu.vs.stpms.lablist.ui.recyclerview.TermAdapter;

public class TermFragment extends Fragment {

    private TermViewModel termViewModel;
    private RecyclerView recyclerView;
    private TermAdapter termAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        termViewModel =
                new ViewModelProvider(this).get(TermViewModel.class);
        View root = inflater.inflate(R.layout.fragment_term, container, false);
        recyclerView = root.findViewById(R.id.rv_term);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Term> terms = new ArrayList<>();
        terms.add(new Term(1,1,1));
        terms.add(new Term(2,1,2));
        terms.add(new Term(3,2,1));
        recyclerView.setAdapter(new TermAdapter(terms));

        return root;
    }
}