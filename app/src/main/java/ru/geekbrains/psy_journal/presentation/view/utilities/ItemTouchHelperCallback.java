package ru.geekbrains.psy_journal.presentation.view.utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.Removable;

import static androidx.recyclerview.widget.ItemTouchHelper.END;
import static androidx.recyclerview.widget.ItemTouchHelper.START;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

	private static final int TEXT_SIZE = 36;
	private static final float SWIPE_THRESHOLD = 0.75f;
	private final SparseArray<PlugInButton> buttons = new SparseArray<>();
	private final Removable removable;

	public ItemTouchHelperCallback(RecyclerView recyclerView) {
		removable = (Removable) recyclerView.getAdapter();
		recyclerView.setOnTouchListener((v, event) -> {
			if (event.getAction() == MotionEvent.ACTION_DOWN){
				for (int i = 0; i < buttons.size(); i++) {
					int position = buttons.keyAt(i);
					if (buttons.get(position).onClick(event.getX(), event.getY())){
						unfastenFromViewHolder(position);
						removable.delete(position);
						break;
					}
				}
			}
			return false;
		});
		attachSwipe(recyclerView);
	}

	private void attachSwipe(RecyclerView recyclerView) {
		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(this);
		itemTouchHelper.attachToRecyclerView(recyclerView);
	}

	@Override
	public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
		int dragFlags = 0;
		int swipeFlags = START | END;
		return makeMovementFlags(dragFlags, swipeFlags);
	}

	@Override
	public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
		return false;
	}

	@Override
	public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
	}

	@Override
	public boolean isItemViewSwipeEnabled() {
		return true;
	}

	@Override
	public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
		return SWIPE_THRESHOLD;
	}

	@Override
	public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
		if (viewHolder == null) return;
		if (hasButton(viewHolder)){
			int position = viewHolder.getAdapterPosition();
			if (isRemovePlugInButton(buttons.get(position).getClickRegion())){
				unfastenFromViewHolder(position);
			}
		} else {
			attachToViewHolder(viewHolder);
		}
	}

	private boolean hasButton(RecyclerView.ViewHolder viewHolder){
		return viewHolder != null && buttons.get(viewHolder.getAdapterPosition()) != null;
	}

	private void attachToViewHolder(@NonNull RecyclerView.ViewHolder viewHolder){
		int index = viewHolder.getAdapterPosition();
		PlugInButton button = createButton(viewHolder);
		buttons.append(index, button);
	}

	private PlugInButton createButton(@NonNull RecyclerView.ViewHolder viewHolder){
		Context context = viewHolder.itemView.getContext();
		return new PlugInButton
			.Builder(context.getString(R.string.delete))
			.setButtonColor(context.getResources().getColor(R.color.colorAccent))
			.setTextColor(context.getResources().getColor(R.color.colorPrimary))
			.setTextSize(TEXT_SIZE)
			.setIcon(context.getDrawable(R.drawable.ic_delete_forever_24dp))
			.build();
	}

	private void unfastenFromViewHolder(int position){
		if (buttons.get(position) != null){
			buttons.remove(position);
		}
	}

	@Override
	public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
		if (hasButton(viewHolder)){
			PlugInButton button = buttons.get(viewHolder.getAdapterPosition());
			RectF buttonRectF = button.getClickRegion();
			View currentView = viewHolder.itemView;
			if (isThereAnOverlap(currentView, button, dX)){
				dX = (button.isRightSide()) ? -buttonRectF.width() : buttonRectF.width();
			} else if (dX > 0){
				button.setRightSide(false);
				buttonRectF.set(0, currentView.getTop(), currentView.getLeft() + dX, currentView.getBottom());
			} else if (dX < 0){
				button.setRightSide(true);
				buttonRectF.set(currentView.getRight() + dX, currentView.getTop(), currentView.getWidth(), currentView.getBottom());
			}
			button.onDraw(c, buttonRectF);
			super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
		}
	}

	private boolean isRemovePlugInButton(RectF buttonRectF){
		return !buttonRectF.isEmpty() &&  buttonRectF.width() < buttonRectF.height() * 0.1f;
	}

	private boolean isThereAnOverlap(View currentView, PlugInButton button, float dX){
		float halfViewHolder = currentView.getWidth() * Constants.HALF;
		RectF buttonRectF = button.getClickRegion();
		return (button.isRightSide()) ? halfViewHolder <= buttonRectF.width() && buttonRectF.width() > halfViewHolder + dX :
			halfViewHolder <= buttonRectF.width() && halfViewHolder < buttonRectF.width() + dX;
	}
}
