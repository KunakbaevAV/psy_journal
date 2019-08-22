package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.ReportPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.ReportingView;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.ReportAdapter;

import static ru.geekbrains.psy_journal.Constants.TAG_ADD_WORK;

public class ReportingFragment extends AbstractReportingFragment implements ReportingView {

    @InjectPresenter ReportPresenter reportPresenter;

	private static final String KEY_ID_OTF = "key idOTF";
	private static final String KEY_CODE_TF = "key codeTF";
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

    public static ReportingFragment newInstanceOfDetailedReport(String codeTF, long from, long unto) {
        ReportingFragment reportFragment = new ReportingFragment();
        Bundle args = new Bundle();
        args.putString(KEY_CODE_TF, codeTF);
        args.putLong(KEY_FROM, from);
        args.putLong(KEY_UNTO, unto);
        reportFragment.setArguments(args);
        return reportFragment;
    }

    @ProvidePresenter
    ReportPresenter providePresenter() {
        ReportPresenter reportPresenter = new ReportPresenter();
        App.getAppComponent().inject(reportPresenter);
        selectReportType(reportPresenter);
        return reportPresenter;
    }

    private void selectReportType(ReportPresenter reportPresenter) {
        if (getArguments() != null) {
            long from = getArguments().getLong(KEY_FROM);
            long unto = getArguments().getLong(KEY_UNTO);
            if (hasIdOtf()) {
                int idOTF = getArguments().getInt(KEY_ID_OTF);
                reportPresenter.initialize(idOTF, from, unto);
            } else {
                String codeTF = getArguments().getString(KEY_CODE_TF);
                reportPresenter.showReportByTF(codeTF, from, unto);
            }
        }
    }

    private boolean hasIdOtf() {
        if (getArguments() != null) {
            int idOTF = getArguments().getInt(KEY_ID_OTF);
            return idOTF > 0;
        }
        return false;
    }

    @Override
    public void showReportByTF(String codeTF, long from, long unto) {
        if (!hasIdOtf()) return;
        if (getActivity() == null) return;
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_master, ReportingFragment.newInstanceOfDetailedReport(codeTF, from, unto), "Tag report")
                .addToBackStack(TAG_ADD_WORK)
                .commit();
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
