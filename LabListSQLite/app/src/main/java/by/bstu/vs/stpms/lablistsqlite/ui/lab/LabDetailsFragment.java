package by.bstu.vs.stpms.lablistsqlite.ui.lab;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import by.bstu.vs.stpms.lablistsqlite.R;
import by.bstu.vs.stpms.lablistsqlite.application.LabListApplication;
import by.bstu.vs.stpms.lablistsqlite.databinding.FragmentLabDetailsBinding;

public class LabDetailsFragment extends Fragment {

    @Inject
    LabViewModel mViewModel;
    private int labId;

    @Override
    public void onAttach(@NonNull Context context) {
        ((LabListApplication) context.getApplicationContext()).appComponent.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        labId = LabDetailsFragmentArgs.fromBundle(getArguments()).getLabId();

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