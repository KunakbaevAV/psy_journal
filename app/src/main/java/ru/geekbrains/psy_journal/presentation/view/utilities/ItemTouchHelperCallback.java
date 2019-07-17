package ru.geekbrains.psy_journal.presentation.view.utilities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.Removable;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

	private final Removable removable;

	public ItemTouchHelperCallback(Removable removable) {
		this.removable = removable;
	}

	@Override
	public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
		int dragFlags = 0;
		int swipeFlags = ItemTouchHelper.START|ItemTouchHelper.END;
		return makeMovementFlags(dragFlags, swipeFlags);
	}

	@Override
	public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
		return false;
	}

	@Override
	public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
		removable.delete(viewHolder.getAdapterPosition());
	}

	@Override
	public boolean isItemViewSwipeEnabled() {
		return true;
	}
}
