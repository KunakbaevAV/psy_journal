package ru.geekbrains.psy_journal.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presenter.DialogFunctionPresenter;
import ru.geekbrains.psy_journal.view.dialogs.adapters.DialogAdapter;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;

public class FunctionDialog extends MvpAppCompatDialogFragment implements Updated{

	public static FunctionDialog newInstance(String title, int id){
		FunctionDialog fragment = new FunctionDialog();
		Bundle args = new Bundle();
		args.putString("key title", title);
		args.putInt("key id", id);
		fragment.setArguments(args);
		return fragment;
	}

	private DialogAdapter adapter;
	private String title;

	@InjectPresenter DialogFunctionPresenter functionPresenter;

	@ProvidePresenter
	DialogFunctionPresenter providePresenter(){
		if (getActivity() != null){
			int id = 0;
			if (getArguments() != null) {
				title = getArguments().getString("key title");
				id = getArguments().getInt("key id");
			}
			AddWorkFragment fragment = (AddWorkFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Tag add work");
			if (fragment != null){
				return new DialogFunctionPresenter(fragment.workPresenter, title, id);
			}
		}
		return null;
	}

	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (getActivity() == null) return super.onCreateDialog(savedInstanceState);
		return new AlertDialog.Builder(getActivity())
			.setTitle(title)
			.setView(createViewList())
			.setNegativeButton("cancel", (dialog, id) -> getActivity()
				.getSupportFragmentManager()
				.beginTransaction()
				.remove(this)
				.commitNow())
			.create();
	}

	private View createViewList(){
		if (getActivity() == null) return null;
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.function_dialog, null);
		RecyclerView recyclerView = view.findViewById(R.id.recycler_dialog);
		adapter = new DialogAdapter(functionPresenter);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setHasFixedSize(true);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		if (getDialog() != null){
			Window window = getDialog().getWindow();
			if (window != null){
				window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				window.setGravity(Gravity.CENTER);
			}
		}
	}

	@Override
	public void update() {
		adapter.notifyDataSetChanged();
	}
}
