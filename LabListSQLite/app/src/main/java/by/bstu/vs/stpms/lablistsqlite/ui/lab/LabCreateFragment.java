package by.bstu.vs.stpms.lablistsqlite.ui.lab;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import by.bstu.vs.stpms.lablistsqlite.R;
import by.bstu.vs.stpms.lablistsqlite.application.LabListApplication;
import by.bstu.vs.stpms.lablistsqlite.databinding.FragmentLabCreateBinding;
import by.bstu.vs.stpms.lablistsqlite.logging.FileLog;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Lab;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LabCreateFragment extends Fragment {

    private final static String TAG = "LabCreateFragment";
    @Inject
    LabViewModel mViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        ((LabListApplication) context.getApplicationContext()).appComponent.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Lab lab = LabCreateFragmentArgs.fromBundle(getArguments()).getLab();
        int subjectId = LabCreateFragmentArgs.fromBundle(getArguments()).getSubjectId();

        mViewModel.setLabLiveData(lab);
        mViewModel.setSubjectId(subjectId);

        FragmentLabCreateBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lab_create, container, false);
        binding.setVm(mViewModel);
        binding.setFragment(this);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    public void save() {
        String name = mViewModel.getLabLiveData().getValue().getName();
        if (name == null || name.isEmpty()) {
            Toast.makeText(getContext(), "Name required", Toast.LENGTH_SHORT).show();
            return;
        }
        Lab lab = mViewModel.getLabLiveData().getValue();
        mViewModel.save()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                        FileLog.getInstance().info(TAG, "save: success " + lab);
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        FileLog.getInstance().error(TAG, "save: " + lab + " save failed", e);
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}