package ru.geekbrains.psy_journal.presentation.view.dialogs.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.Selectable;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.DisplayedSelectedFileViewHolder;

public class SelectionFileDialogAdapter extends RecyclerView.Adapter {

	protected Selectable selectable;

	public SelectionFileDialogAdapter(Selectable selectable) {
		this.selectable = selectable;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_file_dialog_item, parent, false);
		return new SelectionFileViewHolder(view, selectable);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		SelectionFileViewHolder myViewHolder = (SelectionFileViewHolder) holder;
		selectable.bindView(myViewHolder, position);
	}

	@Override
	public int getItemCount() {
		return selectable.getItemCount();
	}

	public static class SelectionFileViewHolder extends RecyclerView.ViewHolder implements
		View.OnClickListener,
		DisplayedSelectedFileViewHolder {

		@Inject Context context;
		@BindDrawable(R.drawable.ic_file_24dp) Drawable fileImage;
		@BindView(R.id.icon_file) ImageView iconFile;
		@BindView(R.id.name_file) CheckedTextView nameFile;

		protected Selectable selectable;
		private static final int NUMBER_OF_BYTES = 1024;

		SelectionFileViewHolder(View view, Selectable selectable) {
			super(view);
			App.getAppComponent().inject(this);
			ButterKnife.bind(this, view);
			this.selectable = selectable;
			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			selectable.selectItem(getAdapterPosition());
			nameFile.setChecked(!nameFile.isChecked());
		}

		@Override
		public void bind(File file, boolean isSelected) {
			iconFile.setImageDrawable(fileImage);
			String text = String.format("%s  %s  %s  ", file.getName(),
				getSizeFile(file), getDateModifiedFile(file));
			setShowFile(text, isSelected);
		}

		protected void setShowFile(String text, boolean isSelected){
			nameFile.setText(text);
			nameFile.setChecked(isSelected);
		}

		private String getSizeFile(File file){
			double sizeFile = file.length();
			int counter = 0;
			while (sizeFile > NUMBER_OF_BYTES){
				sizeFile = sizeFile / NUMBER_OF_BYTES;
				counter++;
			}
			String dimension = (counter == 0) ? context.getString(R.string.bytes) : (counter == 1) ?
				context.getString(R.string.Kb) : (counter == 2) ? context.getString(R.string.Mb) :
				context.getString(R.string.Gb);
			return String.format(Locale.getDefault(), (counter == 0) ? "%.0f %s" : "%.2f %s", sizeFile, dimension);
		}

		private String getDateModifiedFile(File file){
			return new SimpleDateFormat(Constants.PATTERN_DATE, Locale.getDefault())
				.format(new Date(file.lastModified()));
		}
	}
}
