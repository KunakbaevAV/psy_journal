package ru.geekbrains.psy_journal.presentation.view.dialogs.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.Bindable;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.Displayed;

public class EditableDialogAdapter extends RecyclerView.Adapter {

    private final Bindable bindable;

    public EditableDialogAdapter(Bindable bindable) {
        this.bindable = bindable;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.editable_dialog_item, parent, false), bindable);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        bindable.bindView(myViewHolder, position);
    }

    @Override
    public int getItemCount() {
        return bindable.getItemCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Displayed {

        @BindView(R.id.catalog_item)
        TextView item;
        private final Bindable bindable;

        private ViewHolder(final View view, Bindable bindable) {
            super(view);
            this.bindable = bindable;
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            bindable.selectItem(getAdapterPosition());
        }

        @Override
        public void bind(String name) {
            item.setText(name);
        }
    }
}
