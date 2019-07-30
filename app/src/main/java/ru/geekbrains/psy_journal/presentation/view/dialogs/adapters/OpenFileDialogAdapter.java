package ru.geekbrains.psy_journal.presentation.view.dialogs.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.OpenFileDialogPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.IOpenFileViewHolder;

public class OpenFileDialogAdapter extends RecyclerView.Adapter {

    private OpenFileDialogPresenter presenter;

    public OpenFileDialogAdapter(OpenFileDialogPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.open_file_dialog_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        presenter.bindView(myViewHolder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, IOpenFileViewHolder {

        @BindView(R.id.file_name)
        TextView item;

        private ViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            presenter.selectItem(getAdapterPosition());
        }

        @Override
        public void bind(Drawable image, String name) {
            item.setText(name);
            item.setCompoundDrawables(image, null, null, null); //FIXME Не работает отображение картинок файла и папки
        }
    }
}
