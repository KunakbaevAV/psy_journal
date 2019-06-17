package ru.geekbrains.psy_journal.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.model.data.TD;
import ru.geekbrains.psy_journal.presenter.DialogFunctionPresenter;
import ru.geekbrains.psy_journal.presenter.Settable;
import ru.geekbrains.psy_journal.view.dialogs.adapters.DialogAdapter;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;

public class FunctionDialog extends MvpAppCompatDialogFragment implements Updated{

    private static final String KEY_TITLE = "key title";
    private static final String KEY_ID = "key id";
    private Settable settable;

	public static FunctionDialog newInstance(String title, int id){
		FunctionDialog fragment = new FunctionDialog();
		Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putInt(KEY_ID, id);
		fragment.setArguments(args);
		return fragment;
	}
	private DialogAdapter adapter;

	@InjectPresenter DialogFunctionPresenter functionPresenter;

	@ProvidePresenter
	DialogFunctionPresenter providePresenter(){
        if (getArguments() != null) {
            String title = getArguments().getString(KEY_TITLE);
            int id = getArguments().getInt(KEY_ID);
            DialogFunctionPresenter dialogFunctionPresenter = new DialogFunctionPresenter(title, id);
            App.getAppComponent().inject(dialogFunctionPresenter);
            dialogFunctionPresenter.loadData();
            return dialogFunctionPresenter;
        }
        return new DialogFunctionPresenter();
    }

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
                .setTitle(functionPresenter.getTitle())
			.setView(createViewList())
                .setNegativeButton(getResources().getString(R.string.cancel), (dialog, id) -> getActivity()
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
    public void loadData(String title, int id) {
        if (title.equals(getString(R.string.OTF))) {
            functionPresenter.getOTF();
            return;
        }
        if (title.equals(getString(R.string.TF))) {
            functionPresenter.getTF(id);
            return;
        }
        if (title.equals(getString(R.string.TD))) {
            functionPresenter.getTD(id);
        }
    }

	@Override
	public void update() {
		adapter.notifyDataSetChanged();
	}

    @Override
    public void openNewFeature(Functional function) {
        if (settable == null) return;
        String title = functionPresenter.getTitle();
        if (title.equals(getString(R.string.OTF))) {
            settable.openNewFunction(getString(R.string.TF), function.getId());
            return;
        }
        if (title.equals(getString(R.string.TF))) {
            if (function.getCode().equals(Constants.CODE_OF_OTHER_ACTIVITY))
                settable.saveSelectedFunction(function);
            else settable.openNewFunction(getString(R.string.TD), function.getId());
            return;
        }
        if (title.equals(getString(R.string.TD))) {
            settable.saveSelectedFunction(function);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        settable = null;
    }
}
