package by.bstu.vs.stpms.lablist.ui.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import by.bstu.vs.stpms.lablist.databinding.TermItemLayoutBinding;
import by.bstu.vs.stpms.lablist.model.entity.Term;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    ArrayList<Term> termArrayList;

    public TermAdapter(ArrayList<Term> termArrayList) {
        this.termArrayList = termArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TermItemLayoutBinding binding = TermItemLayoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Term term = termArrayList.get(position);
        holder.binding.setTerm(term);

    }

    @Override
    public int getItemCount() {
        return termArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TermItemLayoutBinding binding;

        public ViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }
    }
}
