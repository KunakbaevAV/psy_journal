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
import ru.geekbrains.psy_journal.presenter.Bindable;

public class DialogAdapter extends RecyclerView.Adapter{

	private Bindable bindable;

	public DialogAdapter(Bindable bindable) {
		this.bindable = bindable;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new FunctionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_functions, parent, false), bindable);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		FunctionViewHolder myViewHolder = (FunctionViewHolder) holder;
		bindable.bindView(myViewHolder, position);
	}

	@Override
	public int getItemCount() {
		return bindable.getItemCount();
	}

	public static class FunctionViewHolder extends RecyclerView.ViewHolder implements
		Displayed,
		View.OnClickListener {

		@BindView(R.id.code_fun)TextView codeView;
		@BindView(R.id.name_fun) TextView nameView;
		private Bindable bindable;

		FunctionViewHolder(@NonNull View itemView, Bindable bindable) {
			super(itemView);
			this.bindable = bindable;
			ButterKnife.bind(this, itemView);
			itemView.setOnClickListener(this);
		}

		@Override
		public void bind(String code, String name) {
			codeView.setText(code);
			nameView.setText(name);
		}

		@Override
		public void onClick(View v) {
			bindable.selectItem(getAdapterPosition());
		}
	}
}
