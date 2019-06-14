package ru.geekbrains.psy_journal.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presenter.Settable;
import ru.geekbrains.psy_journal.view.dialogs.adapters.DialogAdapter;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;

public class FunctionDialog extends DialogFragment {

	public static FunctionDialog newInstance(String title){
		FunctionDialog fragment = new FunctionDialog();
		Bundle args = new Bundle();
		args.putString("key title", title);
		fragment.setArguments(args);
		return fragment;
	}

	private Settable settable;
	private DialogAdapter adapter;

	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (getActivity() == null || getArguments() == null) return super.onCreateDialog(savedInstanceState);
		String title = getArguments().getString("key title");
		AddWorkFragment fragment = (AddWorkFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Tag add work");
		if (fragment != null) settable = fragment.workPresenter;
		return new AlertDialog.Builder(getActivity())
			.setTitle(title)
			.setView(createViewList())
			.setNegativeButton("cancel", (dialog, id) -> dialog.dismiss())
			.create();
	}

	@Override
	public void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}

	private View createViewList(){
		if (getActivity() == null) return null;
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.function_dialog, null);
		RecyclerView recyclerView = view.findViewById(R.id.rc);
		adapter = new DialogAdapter(settable);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setHasFixedSize(true);
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		settable = null;
	}
}
