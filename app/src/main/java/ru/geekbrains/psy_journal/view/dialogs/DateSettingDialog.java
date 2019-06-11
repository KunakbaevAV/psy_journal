package ru.geekbrains.psy_journal.view.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import ru.geekbrains.psy_journal.presenter.Settable;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;

public class DateSettingDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

	private Settable settable;

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		final Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (getActivity() != null){
			AddWorkFragment fragment = (AddWorkFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Tag add work");
			if (fragment != null) {
				settable = fragment.workPresenter;
				return new DatePickerDialog(getActivity(), this, year, month, day);
			}
		}
		return super.onCreateDialog(savedInstanceState);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
		settable.setDate(year, month, dayOfMonth);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		settable = null;
	}
}
