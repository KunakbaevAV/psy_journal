package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.os.Bundle;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.ReportPresenter;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.ReportAdapter;

public class ReportingFragment extends AbstractReportingFragment {

	@InjectPresenter ReportPresenter reportPresenter;

	private static final String KEY_ID_OTF = "key idOTF";
    private static final String KEY_FROM = "key from";
    private static final String KEY_UNTO = "key unto";
    private ReportAdapter reportAdapter;

    public static ReportingFragment newInstance(int idOTF, long from, long unto) {
        ReportingFragment reportFragment = new ReportingFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_ID_OTF, idOTF);
        args.putLong(KEY_FROM, from);
        args.putLong(KEY_UNTO, unto);
        reportFragment.setArguments(args);
        return reportFragment;
    }

    @ProvidePresenter
    ReportPresenter providePresenter() {
        ReportPresenter reportPresenter = new ReportPresenter();
        App.getAppComponent().inject(reportPresenter);
        if (getArguments() != null) {
            int idOTF = getArguments().getInt(KEY_ID_OTF);
            long from = getArguments().getLong(KEY_FROM);
            long unto = getArguments().getLong(KEY_UNTO);
            reportPresenter.initialize(idOTF, from, unto);
        }
        return reportPresenter;
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
}
