package ru.geekbrains.psy_journal.view.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import ru.geekbrains.psy_journal.presenter.Settable;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;

public class TimeSettingDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

	private Settable settable;

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (getActivity() != null) {
			AddWorkFragment fragment = (AddWorkFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Tag add work");
			if (fragment != null) {
				settable = fragment.workPresenter;
			}
		}
		return new TimePickerDialog(getActivity(), this, 1, 0, true);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		settable.setHours(hourOfDay, minute);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		settable = null;
	}
}
