package ru.geekbrains.psy_journal.view.dialogs;

import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.presenter.DialogFunctionPresenter;

public class TFDialog extends FunctionDialog{

	static TFDialog newInstance(int id){
		TFDialog fragment = new TFDialog();
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
            DialogFunctionPresenter dialogFunctionPresenter = new DialogFunctionPresenter(getString(R.string.TF));
            App.getAppComponent().inject(dialogFunctionPresenter);
            dialogFunctionPresenter.getTF(id);
            return dialogFunctionPresenter;
        }
        return new DialogFunctionPresenter();
    }

	@Override
	public void openNewFeature(Functional function) {
		if (function.getCode().equals(Constants.CODE_OF_OTHER_ACTIVITY))
			settable.saveSelectedFunction(function);
		else settable.openNewFunction(TDDialog.newInstance(function.getId()), getString(R.string.TD));
	}
}
