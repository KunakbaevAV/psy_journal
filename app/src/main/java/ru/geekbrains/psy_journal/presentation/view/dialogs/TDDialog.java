package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.DialogTDPresenter;
import ru.geekbrains.psy_journal.presentation.view.dialogs.adapters.FunctionDialogAdapter;

public class TDDialog extends FunctionDialog {

	public static TDDialog newInstance(int id){
		TDDialog dialog = new TDDialog();
		Bundle args = new Bundle();
		args.putInt(Constants.KEY_ID, id);
		dialog.setArguments(args);
		return dialog;
	}

	@InjectPresenter DialogTDPresenter tdPresenter;

	@ProvidePresenter
	DialogTDPresenter providePresenter(){
		DialogTDPresenter tdPresenter = new DialogTDPresenter(settableByFunction);
		App.getAppComponent().inject(tdPresenter);
		if (getArguments() != null) {
			tdPresenter.getTD(getArguments().getInt(Constants.KEY_ID));
		}
		return tdPresenter;
	}

	@Override
	protected View createView(){
		View view = super.createView();
		adapter = new FunctionDialogAdapter(tdPresenter);
		recyclerView.setAdapter(adapter);
		return view;
	}

	@Override
	public String getTitle(Context context) {
		return context.getString(R.string.TD);
	}
}
