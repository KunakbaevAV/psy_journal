package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.data.repositories.model.Functional;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.DialogFunctionPresenter;
import ru.geekbrains.psy_journal.presentation.view.fragment.GivenBySettableFunction;

public class OTFDialog extends FunctionDialog{

	public static OTFDialog newInstance(String tag){
		OTFDialog fragment = new OTFDialog();
		Bundle args = new Bundle();
		args.putString(Constants.KEY_TAG, tag);
		fragment.setArguments(args);
		return fragment;
	}

	@InjectPresenter DialogFunctionPresenter functionPresenter;

	@ProvidePresenter
	DialogFunctionPresenter providePresenter(){
		DialogFunctionPresenter dialogFunctionPresenter = new DialogFunctionPresenter(getString(R.string.OTF));
		App.getAppComponent().inject(dialogFunctionPresenter);
		dialogFunctionPresenter.getOTF();
		return dialogFunctionPresenter;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getActivity() == null) return;
		if (getArguments() != null){
			String tag = getArguments().getString(Constants.KEY_TAG);
			GivenBySettableFunction bySelectableFunction = (GivenBySettableFunction) getActivity().getSupportFragmentManager().findFragmentByTag(tag);
			if (bySelectableFunction != null) settableByFunction = bySelectableFunction.getSettableByFunction();
		}
	}

	@Override
	public void openNewFeature(Functional function) {
		settableByFunction.setFunction(function, false);
	}
}
