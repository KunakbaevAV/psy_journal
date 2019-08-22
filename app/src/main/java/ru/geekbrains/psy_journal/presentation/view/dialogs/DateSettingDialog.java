package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

import ru.geekbrains.psy_journal.presentation.presenter.SettableByDate;
import ru.geekbrains.psy_journal.presentation.view.fragment.GivenBySettableDate;

public class DateSettingDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	private static final String KEY_TAG = "key tag";

	public static DateSettingDialog newInstance(String tag){
		DateSettingDialog fragment = new DateSettingDialog();
		Bundle args = new Bundle();
		args.putString(KEY_TAG, tag);
		fragment.setArguments(args);
		return fragment;
	}

	private SettableByDate settableByDate;

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		String tag = "";
		if (getArguments() != null) tag = getArguments().getString(KEY_TAG);
		final Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (getActivity() != null){
			GivenBySettableDate givenBySettableDate = (GivenBySettableDate) getActivity().getSupportFragmentManager().findFragmentByTag(tag);
			if (givenBySettableDate != null) {
				settableByDate = givenBySettableDate.getSettableByDate();
				return new DatePickerDialog(getActivity(), this, year, month, day);
			}
		}
		return super.onCreateDialog(savedInstanceState);
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, dayOfMonth);
		long date = calendar.getTimeInMillis();
		settableByDate.setDate(date);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		settableByDate = null;
	}
}
