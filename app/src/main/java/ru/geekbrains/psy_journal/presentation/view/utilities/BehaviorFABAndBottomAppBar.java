package ru.geekbrains.psy_journal.presentation.view.utilities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
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
		FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) target.getLayoutParams();
		if (isDown){
			BottomAppBar bottomAppBar = coordinatorLayout.findViewById(R.id.bottomAppBar);
			int heightBottomAppBar = 0;
			if (bottomAppBar != null) {
				heightBottomAppBar = bottomAppBar.getHeight();
				bottomAppBar.performShow();
			}
			slideUp(child);
			layoutParams.bottomMargin = child.getHeight() + heightBottomAppBar;
		} else {
			layoutParams.bottomMargin = 0;
		}
		target.setLayoutParams(layoutParams);
	}
}
