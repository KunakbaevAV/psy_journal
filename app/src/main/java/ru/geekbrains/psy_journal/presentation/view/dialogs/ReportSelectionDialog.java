package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.ReportSelectionPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByDate;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByFunction;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.ReportSelectionView;
import ru.geekbrains.psy_journal.presentation.view.fragment.GivenBySettableDate;
import ru.geekbrains.psy_journal.presentation.view.fragment.GivenBySettableFunction;
import ru.geekbrains.psy_journal.presentation.view.fragment.ReportingFragment;

import static ru.geekbrains.psy_journal.Constants.TAG_ADD_WORK;

public class ReportSelectionDialog extends AbstractDialog implements
	ReportSelectionView,
	GivenBySettableDate,
	GivenBySettableFunction {

	@BindView(R.id.otf_text) TextInputEditText otfView;
	@BindView(R.id.report_date_begin_text) TextInputEditText fromView;
	@BindView(R.id.report_date_end_text) TextInputEditText toView;
	@BindView(R.id.otf_layout) TextInputLayout otfLayout;
	@BindView(R.id.report_date_begin_layout) TextInputLayout dateBeginLayout;
	@BindView(R.id.report_date_end_layout) TextInputLayout dateEndLayout;

	@InjectPresenter ReportSelectionPresenter selectionPresenter;

	private final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.PATTERN_DATE, Locale.getDefault());
	private Unbinder unbinder;

	@ProvidePresenter
	ReportSelectionPresenter providePresenter() {
		return new ReportSelectionPresenter();
	}

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
		return getResources().getString(R.string.report_of_generalized_labor_functions);
	}

	private void initialize() {
		if (getActivity() != null) {
			otfView.setOnClickListener(v -> OTFDialog.newInstance(Constants.TAG_OTF_SELECTION).show(getActivity().getSupportFragmentManager(), getString(R.string.OTF)));
			fromView.setOnClickListener(v -> determineDate(true));
			toView.setOnClickListener(v -> determineDate(false));
			hasPositiveButton(true);
			setTextButton(getResources().getString(R.string.report));
		}
	}

	private void determineDate(boolean isFrom) {
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
		if (otfLayout.getError() != null) otfLayout.setError(null);
		otfView.setText(name);
	}

	@Override
	public void closeDialog() {
		if (getActivity() != null) {
			FragmentManager manager = getActivity().getSupportFragmentManager();
			OTFDialog dialog = (OTFDialog) manager.findFragmentByTag(getString(R.string.OTF));
			if (dialog != null) manager.beginTransaction().remove(dialog).commit();
		}
	}

	@Override
	public void showSelectedFrom(long date) {
		if (dateBeginLayout.getError() != null) dateBeginLayout.setError(null);
		fromView.setText(dateFormat.format(date));
	}

	@Override
	public void showSelectedUnto(long date) {
		if (dateEndLayout.getError() != null) dateEndLayout.setError(null);
		toView.setText(dateFormat.format(date));
	}

	@Override
	public void showErrorOTF(int otf) {
		String message = null;
		if (otf == 0) message = "Обобщенная трудовая функция НЕ выбрана";
		otfLayout.setError(message);
	}

	@Override
	public void showErrorFrom(long from) {
		String message;
		if (from == 0) message = "Начальная дата НЕ заполнена";
		else message = "Начальная дата НЕ может быть больше конечной";
		dateBeginLayout.setError(message);
	}

	@Override
	public void showErrorUnto(long unto) {
		String message;
		if (unto == 0) message = "Конечная дата НЕ заполнена";
		else message = "Начальная дата НЕ может быть больше конечной";
		dateEndLayout.setError(message);
	}

	@Override
	public void transferData(int idOTF, long from, long unto) {
		if (getActivity() == null) return;
		getActivity().getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.frame_master, ReportingFragment.newInstance(idOTF, from, unto), "Tag report")
				.addToBackStack(TAG_ADD_WORK)
				.commit();
    }

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

    @Override
    public void onResume() {
        super.onResume();
	    final AlertDialog dialog = (AlertDialog) getDialog();
	    if (dialog != null) {
		    Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
		    positiveButton.setOnClickListener(v -> {
			    if (selectionPresenter.isCollectedAll()) dialog.dismiss();
		    });
	    }
    }
}
