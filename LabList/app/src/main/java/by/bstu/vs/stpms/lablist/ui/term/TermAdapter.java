package by.bstu.vs.stpms.lablist.ui.term;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import by.bstu.vs.stpms.lablist.databinding.TermItemLayoutBinding;
import by.bstu.vs.stpms.lablist.model.entity.Term;
import lombok.Getter;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {
    @Getter
    List<Term> terms;

    public void setTerms(List<Term> terms) {
        this.terms = terms;
        notifyDataSetChanged();
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
        Term term = terms.get(position);
        holder.binding.setTerm(term);

    }

    @Override
    public int getItemCount() {
        return terms == null ? 0 : terms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TermItemLayoutBinding binding;

        public ViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }
    }
}
