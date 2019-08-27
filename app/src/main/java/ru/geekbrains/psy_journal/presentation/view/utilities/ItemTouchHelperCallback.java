package ru.geekbrains.psy_journal.presentation.view.utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.inject.Inject;

import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.Removable;

public class ItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {

    private static final int BUTTON_WIDTH = 200;
    private final RecyclerView recyclerView;
    private List<UnderlayButton> buttons;
    private GestureDetector gestureDetector;
    private int swipedPos = -1;
    private float swipeThreshold = 0.5f;
    private Map<Integer, List<UnderlayButton>> buttonsBuffer;
    private Queue<Integer> recoverQueue;
    @Inject
    Context context;

    private final GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            for (UnderlayButton button : buttons) {
                if (button.onClick(e.getX(), e.getY()))
                    break;
            }
            return true;
        }
    };

    private final View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent e) {
            if (swipedPos < 0) return false;
            Point point = new Point((int) e.getRawX(), (int) e.getRawY());
            RecyclerView.ViewHolder swipedViewHolder = recyclerView.findViewHolderForAdapterPosition(swipedPos);
            if (swipedViewHolder == null) return false;
            View swipedItem = swipedViewHolder.itemView;
            Rect rect = new Rect();
            swipedItem.getGlobalVisibleRect(rect);
            if (e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_MOVE) {
                if (rect.top < point.y && rect.bottom > point.y) {
                    gestureDetector.onTouchEvent(e);
                    view.performClick();
                } else {
                    recoverQueue.add(swipedPos);
                    swipedPos = -1;
                    recoverSwipedItem();
                }
            }
            return false;
        }
    };

    public ItemTouchHelperCallback(RecyclerView recyclerView) {
        super(0, ItemTouchHelper.LEFT);
        App.getAppComponent().inject(this);
        this.recyclerView = recyclerView;
        initializeValues();
        attachSwipe();
    }

    private void initializeValues() {
        this.buttons = new ArrayList<>();
        this.gestureDetector = new GestureDetector(context, gestureListener);
        this.recyclerView.setOnTouchListener(onTouchListener);
        buttonsBuffer = new HashMap<>();
        recoverQueue = new LinkedList<Integer>() {
            @Override
            public boolean add(Integer o) {
                if (contains(o)) {
                    return false;
                } else {
                    return super.add(o);
                }
            }
        };
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        if (swipedPos != pos) {
            recoverQueue.add(swipedPos);
        }
        swipedPos = pos;
        if (buttonsBuffer.containsKey(swipedPos)) {
            buttons = buttonsBuffer.get(swipedPos);
        } else {
            buttons.clear();
        }
        buttonsBuffer.clear();
        swipeThreshold = 0.5f * buttons.size() * BUTTON_WIDTH;
        recoverSwipedItem();
    }

    private synchronized void recoverSwipedItem() {
        if (recyclerView.getAdapter() == null) return;
        while (!recoverQueue.isEmpty()) {
            int pos = recoverQueue.poll();
            if (pos > -1) {
                recyclerView.getAdapter().notifyItemChanged(pos);
            }
        }
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return swipeThreshold;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return 0.1f * defaultValue;
    }

    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {
        return 5.0f * defaultValue;
    }

    @Override
    public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        int pos = viewHolder.getAdapterPosition();
        float translationX = dX;
        View itemView = viewHolder.itemView;
        if (pos < 0) {
            swipedPos = pos;
            return;
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX < 0) {
            List<UnderlayButton> buffer = new ArrayList<>();
            if (!buttonsBuffer.containsKey(pos)) {
                createUnderlayButton(viewHolder, buffer);
                buttonsBuffer.put(pos, buffer);
            } else {
                buffer = buttonsBuffer.get(pos);
            }
            if (buffer == null) return;
            translationX = dX * buffer.size() * BUTTON_WIDTH / itemView.getWidth();
            drawButtons(canvas, itemView, buffer, pos, translationX);
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive);
    }

    private void drawButtons(Canvas canvas, View itemView, List<UnderlayButton> buffer, int pos, float dX) {
        float right = itemView.getRight();
        float dButtonWidth = (-1) * dX / buffer.size();
        for (UnderlayButton button : buffer) {
            float left = right - dButtonWidth;
            button.onDraw(canvas,
                    new RectF(left, itemView.getTop(), right, itemView.getBottom()),
                    pos
            );
            right = left;
        }
    }

    private void attachSwipe() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(this);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void createUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
        Removable removable = (Removable) recyclerView.getAdapter();
        if (removable == null) return;
        underlayButtons.add(new UnderlayButton(
                context.getString(R.string.delete),
                context.getResources().getColor(R.color.colorAccent),
                context.getResources().getColor(R.color.colorPrimary),
                pos -> removable.delete(viewHolder.getAdapterPosition())
        ));
    }

    public interface UnderlayButtonClickListener {
        void onClick(int pos);
    }

}
