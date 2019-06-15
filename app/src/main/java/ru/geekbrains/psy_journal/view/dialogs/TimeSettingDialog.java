package ru.geekbrains.psy_journal.view.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import ru.geekbrains.psy_journal.presenter.Terminable;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;

public class TimeSettingDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

	private static final int DEFAULT_HOUR = 1;
	private static final int DEFAULT_MINUTE = 0;
	private Terminable terminable;

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (getActivity() != null) {
			AddWorkFragment fragment = (AddWorkFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Tag add work");
			if (fragment != null) {
				terminable = fragment.workPresenter;
			}
		}
		return new TimePickerDialog(getActivity(), this, DEFAULT_HOUR, DEFAULT_MINUTE, true);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		terminable.setHours(hourOfDay, minute);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		terminable = null;
	}
}
