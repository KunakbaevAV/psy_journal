package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.ReportPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.ReportingView;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.AdapterReport;

public class ReportingFragment extends MvpAppCompatFragment implements ReportingView {

    private static final String KEY_ID_OTF = "key idOTF";
    private static final String KEY_FROM = "key from";
    private static final String KEY_UNTO = "key unto";
    @BindView(R.id.recycler_all_work)
    RecyclerView recycler;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @InjectPresenter
    ReportPresenter reportPresenter;
    private Unbinder unbinder;
    private AdapterReport adapterReport;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_work, container, false);
        unbinder = ButterKnife.bind(this, view);
        showRecycler();
        return view;
    }

    private void showRecycler() {
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setHasFixedSize(true);
        adapterReport = new AdapterReport(reportPresenter.getRecyclePresenter());
        recycler.setAdapter(adapterReport);
    }

    @Override
    public void updateRecyclerView() {
        adapterReport.notifyDataSetChanged();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
