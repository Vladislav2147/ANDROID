package by.bstu.vs.stpms.lablist.ui.lab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import by.bstu.vs.stpms.lablist.databinding.LabItemLayoutBinding;
import by.bstu.vs.stpms.lablist.model.entity.Lab;
import lombok.Getter;
import lombok.Setter;

public class LabAdapter extends RecyclerView.Adapter<LabAdapter.ViewHolder> {
    public interface OnClickListener {
        void onVariantClick(Lab lab);
    }

    public interface OnLongClickListener {
        boolean onLongVariantClick(Lab lab, View view);
    }

    @Getter
    List<Lab> labs;
    @Setter
    private OnClickListener onClickListener;
    @Setter
    private OnLongClickListener onLongClickListener;

    public void setSubjects(List<Lab> labs) {
        this.labs = labs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LabItemLayoutBinding binding = LabItemLayoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lab lab = labs.get(position);
        holder.binding.setLab(lab);
        if (onClickListener != null) {
            holder.itemView.setOnClickListener(view -> onClickListener.onVariantClick(lab));
        }
        if (onLongClickListener != null) {
            holder.itemView.setOnLongClickListener(view -> onLongClickListener.onLongVariantClick(lab, view));
        }

    }

    @Override
    public int getItemCount() {
        return labs == null? 0 : labs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LabItemLayoutBinding binding;

        public ViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }
    }
}
