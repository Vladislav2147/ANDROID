package by.bstu.vs.stpms.daytracker.ui.business.recylerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

import by.bstu.vs.stpms.daytracker.model.entity.Business;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private BusinessAdapter adapter;
    private Consumer<Business> onSwipe;

    public RecyclerItemTouchHelper(BusinessAdapter adapter, Consumer<Business> onSwipe) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
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
