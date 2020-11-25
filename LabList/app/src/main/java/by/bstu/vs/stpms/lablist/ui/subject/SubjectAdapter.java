package by.bstu.vs.stpms.lablist.ui.subject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import by.bstu.vs.stpms.lablist.databinding.SubjectItemLayoutBinding;
import by.bstu.vs.stpms.lablist.model.entity.Subject;
import lombok.Getter;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    @Getter
    List<Subject> subjects;

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        notifyDataSetChanged();
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
        Subject subject = subjects.get(position);
        holder.binding.setSubject(subject);

    }

    @Override
    public int getItemCount() {
        return subjects == null? 0 : subjects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SubjectItemLayoutBinding binding;

        public ViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }
    }
}
