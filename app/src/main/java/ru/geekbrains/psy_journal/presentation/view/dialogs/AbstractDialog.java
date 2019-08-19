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

abstract class AbstractDialog extends MvpAppCompatDialogFragment {

	private boolean isPositiveButton;
	private boolean isNegativeButton;
	private String textPositiveBut;
	private String textNegativeBut;

	protected void hasPositiveButton(boolean isPositiveButton){
		this.isPositiveButton = isPositiveButton;
	}

	protected void hasNegativeButton(boolean isNegativeButton){
		this.isNegativeButton = isNegativeButton;
	}

	protected void setTextPositiveBut(String textPositiveBut) {
		this.textPositiveBut = textPositiveBut;
	}

	protected void setTextNegativeBut(String textNegativeBut) {
		this.textNegativeBut = textNegativeBut;
	}

	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (getActivity() == null) return super.onCreateDialog(savedInstanceState);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
			.setTitle(getTitle())
				.setView(createView());
			if (isPositiveButton) builder.setPositiveButton(textPositiveBut, null);
			if (isNegativeButton) builder.setNegativeButton(textNegativeBut, null);
		return builder.create();
	}

	protected abstract View createView();

	protected abstract String getTitle();

	@Override
	public void onCancel(@NonNull DialogInterface dialog) {
		super.onCancel(dialog);
		dialog.dismiss();
	}

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
