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
import lombok.Setter;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    public interface OnClickListener {
        void onVariantClick(Term term);
    }

    public interface OnLongClickListener {
        boolean onLongVariantClick(Term term, View view);
    }

    @Getter
    List<Term> terms;
    @Setter
    private OnClickListener onClickListener;
    @Setter
    private OnLongClickListener onLongClickListener;

    public void setTerms(List<Term> terms) {
        this.terms = terms;
        notifyDataSetChanged();
    }

    public Term getItemAt(int position) {
        return terms.get(position);
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
        if (onClickListener != null) {
            holder.itemView.setOnClickListener(view -> onClickListener.onVariantClick(term));
        }
        if (onLongClickListener != null) {
            holder.itemView.setOnLongClickListener(view -> onLongClickListener.onLongVariantClick(term, view));
        }

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
