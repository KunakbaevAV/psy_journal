package ru.geekbrains.psy_journal.view.dialogs.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presenter.EditableDialogPresenter;
import ru.geekbrains.psy_journal.view.fragment.IViewHolderCatalog;

public class EditableDialogAdapter extends RecyclerView.Adapter<EditableDialogAdapter.ViewHolder> {

    private EditableDialogPresenter presenter;

    public EditableDialogAdapter(EditableDialogPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public EditableDialogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.editable_dialog_item, parent, false);
        return new EditableDialogAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditableDialogAdapter.ViewHolder holder, int position) {
        holder.position = position;
        presenter.bindView(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements IViewHolderCatalog, View.OnClickListener {

        @BindView(R.id.catalog_item)
        TextView item;

        private int position = 0;

        private ViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void setCatalogItem(String name) {
            item.setText(name);
        }

        @Override
        public int getPos() {
            return position;
        }

        @Override
        public void onClick(View v) {
            presenter.selectItem(this);
        }
    }
}
