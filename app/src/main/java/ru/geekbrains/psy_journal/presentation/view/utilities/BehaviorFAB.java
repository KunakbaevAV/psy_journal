package ru.geekbrains.psy_journal.presentation.view.utilities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;

public class BehaviorFAB extends HideBottomViewOnScrollBehavior<View> {

	private boolean isDown;

	public BehaviorFAB(Context context, AttributeSet attrs) {
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
            slideUp(child);
		}
	}
}
