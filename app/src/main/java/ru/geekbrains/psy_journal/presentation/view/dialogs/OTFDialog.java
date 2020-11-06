package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.content.Context;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.DialogOTFPresenter;
import ru.geekbrains.psy_journal.presentation.view.dialogs.adapters.FunctionDialogAdapter;

public class OTFDialog extends FunctionDialog{

	@InjectPresenter DialogOTFPresenter otfPresenter;

	@ProvidePresenter
	DialogOTFPresenter providePresenter(){
		DialogOTFPresenter otfPresenter = new DialogOTFPresenter(settableByFunction);
		App.getAppComponent().inject(otfPresenter);
		otfPresenter.getOTF();
		return otfPresenter;
	}

	@Override
	protected View createView(){
		View view = super.createView();
		adapter = new FunctionDialogAdapter(otfPresenter);
		recyclerView.setAdapter(adapter);
		return view;
	}

	@Override
	public String getTitle(Context context) {
		return context.getString(R.string.OTF);
	}
}
