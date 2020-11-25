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

public class LabAdapter extends RecyclerView.Adapter<LabAdapter.ViewHolder> {
    @Getter
    List<Lab> labs;

    public void setLabs(List<Lab> labs) {
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
