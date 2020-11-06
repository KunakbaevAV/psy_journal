package ru.geekbrains.psy_journal.presentation.view.fragment.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.presenter.IRecyclerAllWorkPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.viewholders.IViewHolder;

public class AdapterAllWork extends RecyclerView.Adapter<AdapterAllWork.ViewHolder> implements Removable {

    private final IRecyclerAllWorkPresenter presenter;

    public AdapterAllWork(IRecyclerAllWorkPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_all_work, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        presenter.bindView(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    @Override
    public void delete(int position) {
        presenter.delete(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements
	    IViewHolder,
	    View.OnClickListener {

        @BindView(R.id.date_layout) TextView dateLayout;
        @BindView(R.id.theme) TextView themeLayout;

        private ViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void setWork(String date, String theme) {
            dateLayout.setText(date);
            themeLayout.setText(theme);
        }

        @Override
        public void onClick(View v) {
            presenter.selectItem(getAdapterPosition());
        }
    }
}
