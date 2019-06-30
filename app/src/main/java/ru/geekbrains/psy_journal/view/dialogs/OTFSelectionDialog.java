package ru.geekbrains.psy_journal.view.dialogs;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.model.database.RoomHelper;
import ru.geekbrains.psy_journal.presenter.OTFSelectionPresenter;
import ru.geekbrains.psy_journal.presenter.SettableByDate;
import ru.geekbrains.psy_journal.presenter.SettableByFunction;
import ru.geekbrains.psy_journal.view.fragment.GivenBySettableDate;
import ru.geekbrains.psy_journal.view.fragment.GivenBySettableFunction;

import static ru.geekbrains.psy_journal.Constants.TAG;

public class OTFSelectionDialog extends AbstractDialog implements
	OTFSelectionView,
	GivenBySettableDate,
	GivenBySettableFunction {

	@BindView(R.id.otf_text) TextInputEditText otfView;
	@BindView(R.id.report_date_begin_text) TextInputEditText fromView;
	@BindView(R.id.report_date_end_text) TextInputEditText toView;

	@InjectPresenter OTFSelectionPresenter selectionPresenter;

	@ProvidePresenter OTFSelectionPresenter providePresenter(){
		return new OTFSelectionPresenter();
	}

	private final DialogInterface.OnClickListener listener = (dialog, which) -> {
		if(selectionPresenter.isCollectedAll()) dismiss();
	};
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.PATTERN_DATE, Locale.getDefault());
	private Unbinder unbinder;

	@Override
	protected View createView() {
		if (getActivity() == null) return null;
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_otf_selection, null);
		unbinder = ButterKnife.bind(this, view);
		initialize();
		return view;
	}

	@Override
	protected String getTitle() {
		return "отчет Обобщенных трудовых функций";
	}

	private void initialize(){
		if (getActivity() != null){
			otfView.setOnClickListener(v -> OTFDialog.newInstance(Constants.TAG_OTF_SELECTION).show(getActivity().getSupportFragmentManager(), getString(R.string.OTF)));
			fromView.setOnClickListener(v -> determineDate(true));
			toView.setOnClickListener(v -> determineDate(false));
			hasPositiveButton(true);
			setTextButton("Отчет");
			setListener(listener);
		}
	}

	private void determineDate(boolean isFrom){
		if (getActivity() == null) return;
		selectionPresenter.setFrom(isFrom);
		DateSettingDialog.newInstance(Constants.TAG_OTF_SELECTION).show(getActivity().getSupportFragmentManager(), Constants.TAG_DATE_PICKER);
	}

	@Override
	public SettableByDate getSettableByDate() {
		return selectionPresenter;
	}

	@Override
	public SettableByFunction getSettableByFunction() {
		return selectionPresenter;
	}

	@Override
	public void showSelectedOTF(String name) {
		otfView.setText(name);
	}

	@Override
	public void closeDialog() {
		if (getActivity() != null) {
			FragmentManager manager = getActivity().getSupportFragmentManager();
			OTFDialog dialog = (OTFDialog) manager.findFragmentByTag(getString(R.string.OTF));
			if (dialog != null)	manager.beginTransaction().remove(dialog).commit();
		}
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
        Log.i(TAG, String.valueOf(idOTF));
        Log.i(TAG, String.valueOf(from));
        Log.i(TAG, String.valueOf(unto));
		//TODO здесь запуск фрагмента с отчетом.

        testReport(idOTF, from, unto);
    }

    // тестовый метод проверки данных для отчета
    @SuppressLint("CheckResult")
    private void testReport(int idOTF, long from, long unto) {
        RoomHelper roomHelper = new RoomHelper();
		roomHelper.getLaborFunctionReport(idOTF, from, unto).observeOn(AndroidSchedulers.mainThread())
				.subscribe(journalList -> {
					for (Journal j : journalList) {
						Log.d(TAG, j.getCodeTd() + "\n QuantityPeople: " + j.getQuantityPeople() + "\n WorkTime: " + j.getWorkTime());
                    }
                }, throwable -> {
                });
    }

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
