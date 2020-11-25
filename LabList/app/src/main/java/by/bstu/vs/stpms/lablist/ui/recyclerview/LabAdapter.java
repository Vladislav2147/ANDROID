package by.bstu.vs.stpms.lablist.ui.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import by.bstu.vs.stpms.lablist.databinding.LabItemLayoutBinding;
import by.bstu.vs.stpms.lablist.model.entity.Lab;

public class LabAdapter extends RecyclerView.Adapter<LabAdapter.ViewHolder> {

    ArrayList<Lab> labArrayList;

    public LabAdapter(ArrayList<Lab> labArrayList) {
        this.labArrayList = labArrayList;
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
        Lab lab = labArrayList.get(position);
        holder.binding.setLab(lab);

    }

    @Override
    public int getItemCount() {
        return labArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LabItemLayoutBinding binding;

        public ViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }
    }
}
