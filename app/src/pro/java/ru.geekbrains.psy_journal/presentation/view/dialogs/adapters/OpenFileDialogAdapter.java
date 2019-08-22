package ru.geekbrains.psy_journal.presentation.view.dialogs.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

import butterknife.BindDrawable;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.Openable;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.Selectable;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.DisplayedFileViewHolder;

public class OpenFileDialogAdapter extends SelectionFileDialogAdapter {

	private Openable openable;

    public OpenFileDialogAdapter(Openable openable) {
       super(openable.getSelectable());
       this.openable = openable;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    	View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_file_dialog_item, parent, false);
        return new ViewHolder(view, selectable);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
	    openable.bindView(myViewHolder, position);
    }

    public static class ViewHolder extends SelectionFileViewHolder implements
	    DisplayedFileViewHolder {

	    @BindDrawable(R.drawable.ic_go_up_24dp) Drawable upImage;
	    @BindDrawable(R.drawable.ic_folder_24dp) Drawable folderImage;

        private ViewHolder(final View view, final Selectable selectable) {
            super(view, selectable);
            nameFile.setCheckMarkDrawable(null);
        }

        @Override
        public void onClick(View v) {
            selectable.selectItem(getAdapterPosition());
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
		        super.bind(file, false);
	        }
        }

	    protected void setShowFile(String text, boolean isSelected){
		    nameFile.setText(text);
	    }
    }
}
