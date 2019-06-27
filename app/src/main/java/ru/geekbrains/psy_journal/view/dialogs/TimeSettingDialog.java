package ru.geekbrains.psy_journal.view.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.presenter.Temporal;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;

public class TimeSettingDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

	private static final int DEFAULT_HOUR = 1;
	private static final int DEFAULT_MINUTE = 0;
	private Temporal temporal;

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (getActivity() != null) {
			AddWorkFragment fragment = (AddWorkFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Constants.TAG_ADD_WORK);
			if (fragment != null) {
				temporal = fragment.workPresenter;
			}
		}
		return new TimePickerDialog(getActivity(), this, DEFAULT_HOUR, DEFAULT_MINUTE, true);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		temporal.setHours(hourOfDay, minute);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		temporal = null;
	}
}
