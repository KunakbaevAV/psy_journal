package ru.geekbrains.psy_journal.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presenter.Settable;
import ru.geekbrains.psy_journal.view.dialogs.adapters.DialogAdapter;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;

public class FunctionDialog extends DialogFragment implements Updated{

	private Settable settable;
	private DialogAdapter adapter;
	private TextView titleView;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getActivity() == null) return;
		AddWorkFragment fragment = (AddWorkFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Tag add work");
		if (fragment != null) settable = fragment.workPresenter;
	}

	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (getActivity() == null) return super.onCreateDialog(savedInstanceState);
		return new AlertDialog.Builder(getActivity())
			.setView(createViewList())
			.setNegativeButton("cancel", (dialog, id) -> getActivity()
				.getSupportFragmentManager()
				.beginTransaction()
				.remove(this)
				.commit())
			.create();
	}

	private View createViewList(){
		if (getActivity() == null) return null;
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.function_dialog, null);
		RecyclerView recyclerView = view.findViewById(R.id.recycler_dialog);
		titleView = view.findViewById(R.id.title_dialog);
		adapter = new DialogAdapter(settable.setBind());
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setHasFixedSize(true);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		settable.setUpdated(this);
		if (getDialog() != null){
			Window window = getDialog().getWindow();
			if (window != null){
				window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				window.setGravity(Gravity.CENTER);
			}
		}
	}

	@Override
	public void update(String title) {
		titleView.setText(title);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		settable = null;
	}
}
