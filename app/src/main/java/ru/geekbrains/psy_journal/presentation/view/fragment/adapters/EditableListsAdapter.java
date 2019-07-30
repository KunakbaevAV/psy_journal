package ru.geekbrains.psy_journal.presentation.view.fragment.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

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

	private Editable editable;

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
		notifyItemRemoved(pos);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements
		Displayed {

		@Inject InputMethodManager imm;
		@BindView(R.id.textView) AppCompatEditText textView;

		private Editable editable;

		private ViewHolder(final View view, Editable editable) {
			super(view);
			this.editable = editable;
			App.getAppComponent().inject(this);
			ButterKnife.bind(this, view);
			setListenerItemView();
			setListenerTextView();
		}

		@Override
		public void bind(String name) {
			textView.setText(name);
		}

		private void setListenerItemView(){
			itemView.setOnClickListener(this::openKeyBoard);
		}

		private void setListenerTextView(){
			textView.setOnEditorActionListener((v, actionId, event) -> {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					checkTextView();
					closeKeyBoard();
					return true;
				}
				return false;
			});
		}

		private void openKeyBoard(View view){
			textView.setCursorVisible(true);
			imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
		}

		private void checkTextView(){
			if (textView.getText() == null || textView.getText().toString().equals("")){
				editable.selectItem(null, getAdapterPosition());
				return;
			}
			editable.selectItem(textView.getText().toString(), getAdapterPosition());
		}

		private void closeKeyBoard() {
			imm.hideSoftInputFromWindow(itemView.getWindowToken(), 0);
			textView.setCursorVisible(false);
		}
	}
}
