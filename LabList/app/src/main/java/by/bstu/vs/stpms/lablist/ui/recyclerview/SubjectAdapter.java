package by.bstu.vs.stpms.lablist.ui.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import by.bstu.vs.stpms.lablist.databinding.SubjectItemLayoutBinding;
import by.bstu.vs.stpms.lablist.model.entity.Subject;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    ArrayList<Subject> subjectArrayList;

    public SubjectAdapter(ArrayList<Subject> subjectArrayList) {
        this.subjectArrayList = subjectArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SubjectItemLayoutBinding binding = SubjectItemLayoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Subject subject = subjectArrayList.get(position);
        holder.binding.setSubject(subject);

    }

    @Override
    public int getItemCount() {
        return subjectArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SubjectItemLayoutBinding binding;

        public ViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }
    }
}
