package by.bstu.vs.stpms.daytracker.ui.business.recylerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

import by.bstu.vs.stpms.daytracker.model.entity.Business;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private final BusinessAdapter adapter;
    private final Consumer<Business> onSwipe;

    public RecyclerItemTouchHelper(BusinessAdapter adapter, Consumer<Business> onSwipe) {
        super(0, ItemTouchHelper.LEFT);
        this.adapter = adapter;
        this.onSwipe = onSwipe;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        onSwipe.accept(adapter.getBusinesses().get(viewHolder.getAdapterPosition()));
    }
}
