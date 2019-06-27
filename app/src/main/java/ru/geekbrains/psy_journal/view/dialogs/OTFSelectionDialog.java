package ru.geekbrains.psy_journal.view.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.model.data.OTF;
import ru.geekbrains.psy_journal.presenter.OTFSelectionPresenter;
import ru.geekbrains.psy_journal.presenter.Terminable;
import ru.geekbrains.psy_journal.view.fragment.Dated;

public class OTFSelectionDialog extends AbstractDialog implements
	Selectable,
	Dated,
	AdapterView.OnItemSelectedListener{

	@BindView(R.id.spin) Spinner spinnerView;
	@BindView(R.id.report_date_begin_text) TextInputEditText fromView;
	@BindView(R.id.report_date_end_text) TextInputEditText toView;

	@InjectPresenter OTFSelectionPresenter selectionPresenter;

	@ProvidePresenter OTFSelectionPresenter providePresenter(){
		OTFSelectionPresenter selectionPresenter = new OTFSelectionPresenter();
		App.getAppComponent().inject(selectionPresenter);
		selectionPresenter.getOTF();
		return selectionPresenter;
	}

	private final DialogInterface.OnClickListener listener = (dialog, which) -> selectionPresenter.isCollectedAll();
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.PATTERN_DATE, Locale.getDefault());
	private Unbinder unbinder;

	@Override
	protected View createView() {
		if (getActivity() == null) return null;
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_otf_selection, null);
		unbinder = ButterKnife.bind(this, view);
		OTFAdapter otfAdapter = greatAdapter();
		if (otfAdapter != null)otfAdapter.setDropDownViewResource(R.layout.item_functions);
		spinnerView.setAdapter(otfAdapter);
		initialize();
		return view;
	}

	@Override
	protected String getTitle() {
		return "отчет Обобщенных трудовых функций";
	}

	private void initialize(){
		fromView.setOnClickListener(v -> determineDate(true));
		toView.setOnClickListener(v -> determineDate(false));
		hasPositiveButton(true);
		setTextButton("Отчет");
		setListener(listener);
	}

	private void determineDate(boolean isFrom){
		if (getActivity() == null) return;
		selectionPresenter.setFrom(isFrom);
		DateSettingDialog.newInstance("Tag OTFSelection").show(getActivity().getSupportFragmentManager(), Constants.TAG_DATE_PICKER);
	}

	private OTFAdapter greatAdapter(){
		if (getContext() == null) return null;
		return new OTFAdapter(getContext(), R.layout.item_functions);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

		Log.i("position: ", String.valueOf(position));
		TextView textView = (TextView) view;
		String item = textView.getText().toString();
		Log.i("onItemSelected: ", item);
		OTF otf = (OTF) parent.getItemAtPosition(position);
		Log.i("OTF: ", String.valueOf(otf.getName()));
//		selectionPresenter.setSelectedOTF(idOTF);
		parent.setSelection(position);
		spinnerView.setSelection(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	@Override
	public Terminable getTerminable() {
		return selectionPresenter;
	}

	@Override
	public void fillAdapter(List<OTF> list) {
		OTFAdapter otfAdapter = (OTFAdapter) spinnerView.getAdapter();
		otfAdapter.addAll(list);
		otfAdapter.notifyDataSetChanged();
		spinnerView.setOnItemSelectedListener(this);
	}

	@Override
	public void showSelectedFrom(long date) {
		fromView.setText(dateFormat.format(date));
	}

	@Override
	public void showSelectedUnto(long date) {
		toView.setText(dateFormat.format(date));
	}

	@Override
	public void transferData(int idOTF, long from, long unto){
		Log.i("transferData: ", String.valueOf(idOTF));
		Log.i("transferData: ", String.valueOf(from));
		Log.i("transferData: ", String.valueOf(unto));
		//TODO здесь запуск фрагмента с отчетом.

		dismiss();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	private static class OTFAdapter extends ArrayAdapter<OTF> {

		OTFAdapter(@NonNull Context context, int resource) {
			super(context, resource);
		}

		@NonNull
		@Override
		public View getView(int position, View convertView, @NonNull ViewGroup parent) {
			TextView textView;
			if (convertView != null) {
				textView = convertView.findViewById(R.id.name_fun);
			} else {
				View view = LayoutInflater.from(getContext()).inflate(R.layout.item_functions, parent, false);
				view.setClickable(false);
				textView = view.findViewById(R.id.name_fun);
			}
			OTF otf = getItem(position);
			if (otf != null) textView.setText(otf.getName());
			return textView;
		}

		@Override
		public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
			TextView textView;
			if (convertView != null) {
				textView = convertView.findViewById(R.id.name_fun);
			} else {
				View view = LayoutInflater.from(getContext()).inflate(R.layout.item_functions, parent, false);
				view.setClickable(false);
				textView = view.findViewById(R.id.name_fun);
			}
			OTF otf = getItem(position);
			if (otf != null) textView.setText(otf.getName());
			return textView;
		}
	}
}
