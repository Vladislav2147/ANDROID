package by.bstu.vs.stpms.lablistsqlite.ui.subject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.databinding.SubjectItemLayoutBinding;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Subject;
import lombok.Getter;
import lombok.Setter;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    public interface OnClickListener {
        void onVariantClick(Subject subject);
    }

    public interface OnLongClickListener {
        boolean onLongVariantClick(Subject subject, View view);
    }

    @Getter
    List<Subject> subjects;
    @Setter
    private OnClickListener onClickListener;
    @Setter
    private OnLongClickListener onLongClickListener;


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
        if (onClickListener != null) {
            holder.itemView.setOnClickListener(view -> onClickListener.onVariantClick(subject));
        }
        if (onLongClickListener != null) {
            holder.itemView.setOnLongClickListener(view -> onLongClickListener.onLongVariantClick(subject, view));
        }
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
