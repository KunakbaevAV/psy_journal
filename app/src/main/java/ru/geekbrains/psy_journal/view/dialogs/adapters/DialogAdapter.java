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
import ru.geekbrains.psy_journal.presenter.Settable;

public class DialogAdapter extends RecyclerView.Adapter{

	private Settable settable;

	public DialogAdapter(Settable settable) {
		this.settable = settable;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new FunctionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_functions, parent, false), settable);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		FunctionViewHolder myViewHolder = (FunctionViewHolder) holder;
		settable.bindView(myViewHolder, position);
	}

	@Override
	public int getItemCount() {
		return settable.getItemCount();
	}

	public static class FunctionViewHolder extends RecyclerView.ViewHolder implements Displayed, View.OnClickListener {

		@BindView(R.id.code_fun)TextView codeView;
		@BindView(R.id.name_fun) TextView nameView;
		private Settable settable;

		FunctionViewHolder(@NonNull View itemView, Settable settable) {
			super(itemView);
			this.settable = settable;
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
			settable.choose(getAdapterPosition());
		}
	}
}
