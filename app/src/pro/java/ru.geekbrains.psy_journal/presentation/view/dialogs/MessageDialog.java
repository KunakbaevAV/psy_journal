package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.view.activities.Responded;

public class MessageDialog extends DialogFragment implements DialogInterface.OnClickListener {

	private static final String KEY_MESSAGE = "key message";

	public static MessageDialog newInstance(String message){
		MessageDialog fragment = new MessageDialog();
		Bundle args = new Bundle();
		args.putString(KEY_MESSAGE, message);
		fragment.setArguments(args);
		return fragment;
	}

	private Responded responded;

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		responded = (Responded) context;
	}

	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (getActivity() == null) return super.onCreateDialog(savedInstanceState);
		String message = null;
		if (getArguments() != null){
			message = getArguments().getString(KEY_MESSAGE);
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
			.setTitle(getResources().getString(R.string.caution))
			.setMessage(message)
			.setIcon(R.drawable.ic_warning_24dp)
			.setNegativeButton(getResources().getString(R.string.no), this)
			.setPositiveButton(getResources().getString(R.string.yes), this);
		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which){
			case DialogInterface.BUTTON_NEGATIVE:
				responded.refuse();
				break;
			case DialogInterface.BUTTON_POSITIVE:
				responded.toAccept();
				break;
		}
		dialog.dismiss();
	}

	@Override
	public void onCancel(@NonNull DialogInterface dialog) {
		super.onCancel(dialog);
		responded.toCancel();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		responded = null;
	}
}
