package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;

import ru.geekbrains.psy_journal.R;

public abstract class AbstractDialog extends MvpAppCompatDialogFragment {

	private boolean isPositiveButton;
	private String textButton;
	private DialogInterface.OnClickListener listener;

	protected void hasPositiveButton(boolean isPositiveButton){
		this.isPositiveButton = isPositiveButton;
	}

	protected void setTextButton(String textButton) {
		this.textButton = textButton;
	}

	protected void setListener(DialogInterface.OnClickListener listener) {
		this.listener = listener;
	}

	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (getActivity() == null) return super.onCreateDialog(savedInstanceState);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
			.setTitle(getTitle())
			.setView(createView())
			.setNegativeButton(getResources().getString(R.string.cancel), (dialog, id) -> getActivity()
				.getSupportFragmentManager()
				.beginTransaction()
				.remove(this)
				.commitNow());
			if (isPositiveButton) builder.setPositiveButton(textButton, listener);
		return builder.create();
	}

	protected abstract View createView();

	protected abstract String getTitle();

	@Override
	public void onStart() {
		super.onStart();
		if (getDialog() != null){
			Window window = getDialog().getWindow();
			if (window != null){
				window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				window.setGravity(Gravity.CENTER);
			}
		}
	}
}
