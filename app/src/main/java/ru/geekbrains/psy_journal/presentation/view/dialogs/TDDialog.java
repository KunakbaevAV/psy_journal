package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.data.repositories.model.Functional;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.DialogFunctionPresenter;

public class TDDialog extends OTFDialog {

	public static TDDialog newInstance(int id, String tag){
		TDDialog fragment = new TDDialog();
		Bundle args = new Bundle();
		args.putInt(Constants.KEY_ID, id);
		args.putString(Constants.KEY_TAG, tag);
		fragment.setArguments(args);
		return fragment;
	}

	@InjectPresenter
	DialogFunctionPresenter functionPresenter;

	@ProvidePresenter
	DialogFunctionPresenter providePresenter(){
		if (getArguments() != null) {
			int id = getArguments().getInt(Constants.KEY_ID);
			DialogFunctionPresenter dialogFunctionPresenter = new DialogFunctionPresenter(getString(R.string.TD));
			App.getAppComponent().inject(dialogFunctionPresenter);
			dialogFunctionPresenter.getTD(id);
			return dialogFunctionPresenter;
		}
		return new DialogFunctionPresenter();
	}

	@Override
	public void openNewFeature(Functional function) {
		settableByFunction.setFunction(function, true);
	}
}
