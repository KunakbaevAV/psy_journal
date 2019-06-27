package ru.geekbrains.psy_journal.view.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;
import ru.geekbrains.psy_journal.presenter.Terminable;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;
import ru.geekbrains.psy_journal.view.fragment.Dated;

public class DateSettingDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

	public static DateSettingDialog newInstance(String tag){
		DateSettingDialog fragment = new DateSettingDialog();
		Bundle args = new Bundle();
		args.putString("key tag", tag);
		fragment.setArguments(args);
		return fragment;
	}

	private Terminable terminable;

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		String tag = "";
		if (getArguments() != null) tag = getArguments().getString("key tag");
		final Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (getActivity() != null){
			Dated dated = (Dated) getActivity().getSupportFragmentManager().findFragmentByTag(tag);
			if (dated != null) {
				terminable = dated.getTerminable();
				return new DatePickerDialog(getActivity(), this, year, month, day);
			}
		}
		return super.onCreateDialog(savedInstanceState);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
		terminable.setDate(year, month, dayOfMonth);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		terminable = null;
	}
}
