package by.bstu.vs.stpms.lablist.ui.lab;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.function.Consumer;

import by.bstu.vs.stpms.lablist.R;
import by.bstu.vs.stpms.lablist.databinding.FragmentLabDetailsBinding;

public class LabDetailsFragment extends Fragment {

    private LabViewModel mViewModel;
    private Consumer<SQLiteException> showError;
    private int labId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        showError = e -> Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        labId = LabDetailsFragmentArgs.fromBundle(getArguments()).getLabId();
        mViewModel = new ViewModelProvider(this).get(LabViewModel.class);
        FragmentLabDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lab_details, container, false);
        binding.setVm(mViewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.getLabById(labId);

    }

}