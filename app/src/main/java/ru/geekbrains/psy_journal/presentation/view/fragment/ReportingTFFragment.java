package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.os.Bundle;

import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.ReportPresenter;

class ReportingTFFragment extends ReportingOTFFragment {

	static ReportingTFFragment newInstanceOfDetailedReport(String codeTF, long from, long unto) {
		ReportingTFFragment reportFragment = new ReportingTFFragment();
		Bundle args = new Bundle();
		args.putString(KEY_CODE_TF, codeTF);
		args.putLong(Constants.KEY_FROM, from);
		args.putLong(Constants.KEY_UNTO, unto);
		reportFragment.setArguments(args);
		return reportFragment;
	}

	private static final String KEY_CODE_TF = "key codeTF";

	@ProvidePresenter
	ReportPresenter providePresenter() {
		ReportPresenter reportPresenter = new ReportPresenter();
		App.getAppComponent().inject(reportPresenter);
		initReport(reportPresenter);
		return reportPresenter;
	}

    private void initReport(ReportPresenter reportPresenter) {
        if (getArguments() != null) {
            long from = getArguments().getLong(Constants.KEY_FROM);
            long unto = getArguments().getLong(Constants.KEY_UNTO);
            String codeTF = getArguments().getString(KEY_CODE_TF);
            reportPresenter.showReportByTF(codeTF, from, unto);
        }
    }
}
