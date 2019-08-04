package ru.geekbrains.psy_journal.presentation.view.dialogs.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.Openable;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.DisplayedFileViewHolder;

public class OpenFileDialogAdapter extends RecyclerView.Adapter {

    private final Openable openable;

    public OpenFileDialogAdapter(Openable openable) {
        this.openable = openable;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    	View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_file_dialog_item, parent, false);
        return new ViewHolder(view, openable);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        openable.bindView(myViewHolder, position);
    }

    @Override
    public int getItemCount() {
        return openable.getItemCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements
	    View.OnClickListener,
	    DisplayedFileViewHolder {

	    @BindDrawable(R.drawable.ic_go_up_24dp) Drawable upImage;
    	@BindDrawable(R.drawable.ic_file_24dp) Drawable fileImage;
	    @BindDrawable(R.drawable.ic_folder_24dp) Drawable folderImage;
	    @BindView(R.id.icon_file) ImageView iconFile;
        @BindView(R.id.name_file) TextView nameFile;

	    private static final int NUMBER_OF_BYTES = 1024;
        private final Openable openable;

        private ViewHolder(final View view, Openable openable) {
            super(view);
            ButterKnife.bind(this, view);
            this.openable = openable;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            openable.selectItem(getAdapterPosition());
        }

        @Override
        public void bind(File file) {
        	if (file.getName().equals(Constants.SUB_LEVEL)){
        		iconFile.setImageDrawable(upImage);
		        nameFile.setText(file.getName());
		        return;
	        }
            if (file.isDirectory()){
	            iconFile.setImageDrawable(folderImage);
	            nameFile.setText(file.getName());
            } else {
	            iconFile.setImageDrawable(fileImage);
	            nameFile.setText(String.format("%s %s %s", file.getName(),
		            getSizeFile(file), getDateModifiedFile(file)));
            }
        }

	    private String getSizeFile(File file){
		    double sizeFile = file.length();
		    int counter = 0;
		    while (sizeFile > NUMBER_OF_BYTES){
			    sizeFile = sizeFile / NUMBER_OF_BYTES;
			    counter++;
		    }
		    String dimension = (counter == 0) ? "байт" : (counter == 1) ? "Кб" : (counter == 2) ? "Мб" : "Гб";
		    return String.format(Locale.getDefault(), (counter == 0) ? "%.0f %s" : "%.3f %s", sizeFile, dimension);
	    }

	    private String getDateModifiedFile(File file){
        	return new SimpleDateFormat(Constants.PATTERN_DATE, Locale.getDefault())
		        .format(new Date(file.lastModified()));
	    }
    }
}
