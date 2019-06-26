package ru.geekbrains.psy_journal.view.dialogs;

import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.presenter.DialogFunctionPresenter;

public class TDDialog extends FunctionDialog {

	static TDDialog newInstance(int id){
		TDDialog fragment = new TDDialog();
		Bundle args = new Bundle();
		args.putInt(Constants.KEY_ID, id);
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
		settable.saveSelectedFunction(function);
	}
}
