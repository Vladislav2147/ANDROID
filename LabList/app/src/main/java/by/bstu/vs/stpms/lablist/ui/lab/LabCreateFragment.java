package by.bstu.vs.stpms.lablist.ui.lab;


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

import by.bstu.vs.stpms.lablist.R;
import by.bstu.vs.stpms.lablist.databinding.FragmentLabCreateBinding;
import by.bstu.vs.stpms.lablist.model.entity.Lab;

public class LabCreateFragment extends Fragment {


    private LabViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Lab lab = LabCreateFragmentArgs.fromBundle(getArguments()).getLab();
        int subjectId = LabCreateFragmentArgs.fromBundle(getArguments()).getSubjectId();

        mViewModel = new ViewModelProvider(this).get(LabViewModel.class);
        mViewModel.setLabLiveData(lab);
        mViewModel.setSubjectId(subjectId);
        mViewModel.setOnError(e -> Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show());

        FragmentLabCreateBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lab_create, container, false);
        binding.setVm(mViewModel);
        binding.setFragment(this);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void save() {
        mViewModel.save();
        getActivity().onBackPressed();
    }
}