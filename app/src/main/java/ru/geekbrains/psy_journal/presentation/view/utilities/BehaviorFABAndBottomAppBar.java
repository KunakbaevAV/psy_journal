package ru.geekbrains.psy_journal.presentation.view.utilities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.bottomappbar.BottomAppBar;

import ru.geekbrains.psy_journal.R;

public class BehaviorFABAndBottomAppBar extends HideBottomViewOnScrollBehavior<View> {

	private boolean isDown;

	public BehaviorFABAndBottomAppBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void slideUp(View child) {
		super.slideUp(child);
		isDown = false;
	}

	@Override
	public void slideDown(View child) {
		super.slideDown(child);
		isDown = true;
	}

	@Override
	public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
	                               @NonNull View child,
	                               @NonNull View target, int type) {
		if (isDown){
			toLift(coordinatorLayout, child, target);
		} else {
			toBringBack(target);
		}
	}

	private void toBringBack(View target){
		CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) target.getLayoutParams();
		if (layoutParams.bottomMargin == 0) return;
		layoutParams.bottomMargin = 0;
		target.setLayoutParams(layoutParams);
	}

	private void toLift(CoordinatorLayout coordinatorLayout,
	                    View child, View target){
		CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) target.getLayoutParams();
		BottomAppBar bottomAppBar = coordinatorLayout.findViewById(R.id.bottomAppBar);
		int heightBottomAppBar = 0;
		if (bottomAppBar != null) {
			heightBottomAppBar = bottomAppBar.getHeight();
			bottomAppBar.performShow();
		}
		slideUp(child);
		layoutParams.bottomMargin = child.getHeight() / 2 + heightBottomAppBar;
		target.setLayoutParams(layoutParams);
	}
}
