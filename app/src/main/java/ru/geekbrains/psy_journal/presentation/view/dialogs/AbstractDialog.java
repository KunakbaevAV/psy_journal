package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;

import butterknife.Unbinder;

abstract class AbstractDialog extends MvpAppCompatDialogFragment {

	Unbinder unbinder;
	private boolean isPositiveButton;
	private boolean isNegativeButton;
	private String textPositiveBut;
	private String textNegativeBut;

	void hasPositiveButton(boolean isPositiveButton){
		this.isPositiveButton = isPositiveButton;
	}

	void hasNegativeButton(boolean isNegativeButton){
		this.isNegativeButton = isNegativeButton;
	}

	void setTextPositiveBut(String textPositiveBut) {
		this.textPositiveBut = textPositiveBut;
	}

	void setTextNegativeBut(String textNegativeBut) {
		this.textNegativeBut = textNegativeBut;
	}

	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (getActivity() == null) return super.onCreateDialog(savedInstanceState);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
			.setTitle(getTitle(getActivity()))
				.setView(createView());
			if (isPositiveButton) builder.setPositiveButton(textPositiveBut, null);
			if (isNegativeButton) builder.setNegativeButton(textNegativeBut, null);
		return builder.create();
	}

	protected abstract View createView();

	public abstract String getTitle(Context context);

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

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
