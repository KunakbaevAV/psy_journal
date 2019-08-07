package ru.geekbrains.psy_journal.presentation.view.fragment.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.viewholders.ReportRelated;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.viewholders.ReportShown;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private final ReportRelated related;

    public ReportAdapter(ReportRelated related) {
        this.related = related;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.block_report_all, parent, false);
        return new ReportViewHolder(view, related);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        related.bindView(holder, position);
    }

    @Override
    public int getItemCount() {
        return related.getItemCount();
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder implements ReportShown, View.OnClickListener {

        @BindView(R.id.report_label_tf)
        EditText tfView;
        @BindView(R.id.report_quantity_people_tf)
        TextView quantityView;
        @BindView(R.id.report_work_time_tf)
        TextView workTimeView;
        private final ReportRelated related;

        private ReportViewHolder(final View view, ReportRelated related) {
            super(view);
            this.related = related;
            ButterKnife.bind(this, view);
            tfView.setOnClickListener(this);
        }

        @Override
        public void show(String tf, int quantityPeople, float workTime) {
            tfView.setText(tf);
            quantityView.setText(String.valueOf(quantityPeople));
            workTimeView.setText(String.valueOf(workTime));
        }

        @Override
        public void onClick(View v) {
            related.selectItem(getAdapterPosition());
        }
    }
}
