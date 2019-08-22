package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.ReportPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.ReportingView;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.ReportAdapter;

import static ru.geekbrains.psy_journal.Constants.TAG_ADD_WORK;

public class ReportingOTFFragment extends AbstractFragment  implements ReportingView {

	public static ReportingOTFFragment newInstance(int idOTF, long from, long unto) {
		ReportingOTFFragment reportFragment = new ReportingOTFFragment();
		Bundle args = new Bundle();
		args.putInt(KEY_ID_OTF, idOTF);
		args.putLong(Constants.KEY_FROM, from);
		args.putLong(Constants.KEY_UNTO, unto);
		reportFragment.setArguments(args);
		return reportFragment;
	}

	@InjectPresenter ReportPresenter reportPresenter;

	private static final String KEY_ID_OTF = "key idOTF";
	private static final String TAG_REPORT_TF = "Tag report TF";
	private ReportAdapter reportAdapter;

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
			int idOTF = getArguments().getInt(KEY_ID_OTF);
			reportPresenter.initialize(idOTF, from, unto);
		}
	}

	@Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_work, container, false);
        unbinder = ButterKnife.bind(this, view);
        showRecycler();
        return view;
    }

	protected void showRecycler() {
		super.showRecycler();
		reportAdapter = new ReportAdapter(reportPresenter.getRecyclePresenter());
		recycler.setAdapter(reportAdapter);
	}

	@Override
	public void updateRecyclerView() {
		reportAdapter.notifyDataSetChanged();
	}

	@Override
	public void showReportByTF(String codeTF, long from, long unto) {
		if (getActivity() == null) return;
		getActivity().getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.frame_master, ReportingTFFragment.newInstanceOfDetailedReport(codeTF, from, unto), TAG_REPORT_TF)
			.addToBackStack(TAG_ADD_WORK)
			.commit();
	}
}

