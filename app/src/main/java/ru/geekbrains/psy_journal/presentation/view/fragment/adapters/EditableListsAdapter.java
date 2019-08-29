package ru.geekbrains.psy_journal.presentation.view.fragment.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.Editable;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.Displayed;

public class EditableListsAdapter extends RecyclerView.Adapter<EditableListsAdapter.ViewHolder> implements Removable{

	private final Editable editable;

	public EditableListsAdapter(Editable editable) {
		this.editable = editable;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_list, parent, false);
		return new ViewHolder(view, editable);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		editable.bindView(holder, position);
	}

	@Override
	public int getItemCount() {
		return editable.getItemCount();
	}

	@Override
	public void delete(int pos){
		editable.delete(pos);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements
		Displayed {

		@Inject InputMethodManager imm;
		@BindView(R.id.textView) AppCompatEditText editText;
		@BindView(R.id.catalog_item) TextView textView;

		private final Editable editable;

		private ViewHolder(final View view, Editable editable) {
			super(view);
			this.editable = editable;
			App.getAppComponent().inject(this);
			ButterKnife.bind(this, view);
			if (editable.isEditable()){
				textView.setVisibility(View.INVISIBLE);
				editText.setVisibility(View.VISIBLE);
				setListenerItemView();
				setListenerTextView();
			} else {
				editText.setVisibility(View.INVISIBLE);
				textView.setVisibility(View.VISIBLE);
				itemView.setOnClickListener(v -> editable.selectItem(getAdapterPosition()));
			}
		}

		@Override
        public void bind(String name) {
			if (editable.isEditable()) {
				editText.setText(name);
			} else {
				textView.setText(name);
			}
		}

		private void setListenerItemView(){
			itemView.setOnClickListener(this::openKeyBoard);
		}

		private void setListenerTextView(){
			editText.setOnEditorActionListener((v, actionId, event) -> {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					checkTextView();
					closeKeyBoard();
					return true;
				}
				return false;
			});
		}

		private void openKeyBoard(View view){
			editText.setCursorVisible(true);
			imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
		}

		private void checkTextView(){
			if (editText.getText() == null || editText.getText().toString().equals("")){
				editable.selectItem(null, getAdapterPosition());
				return;
			}
			editable.selectItem(editText.getText().toString(), getAdapterPosition());
		}

		private void closeKeyBoard() {
			imm.hideSoftInputFromWindow(itemView.getWindowToken(), 0);
			editText.setCursorVisible(false);
		}
	}
}
