package ru.geekbrains.psy_journal.view.dialogs;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.presenter.DialogFunctionPresenter;

public class OTFDialog extends FunctionDialog{

	@InjectPresenter DialogFunctionPresenter functionPresenter;

	@ProvidePresenter
	DialogFunctionPresenter providePresenter(){
		DialogFunctionPresenter dialogFunctionPresenter = new DialogFunctionPresenter(getString(R.string.OTF));
		App.getAppComponent().inject(dialogFunctionPresenter);
		dialogFunctionPresenter.getOTF();
		return dialogFunctionPresenter;
	}

	@Override
	public void openNewFeature(Functional function) {
		settable.openNewFunction(TFDialog.newInstance(function.getId()), getString(R.string.TF));
	}
}
