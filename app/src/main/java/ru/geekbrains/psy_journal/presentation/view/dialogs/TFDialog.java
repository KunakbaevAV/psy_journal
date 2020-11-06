package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.DialogTFPresenter;
import ru.geekbrains.psy_journal.presentation.view.dialogs.adapters.FunctionDialogAdapter;

public class TFDialog extends FunctionDialog{

	public static TFDialog newInstance(int id){
		TFDialog fragment = new TFDialog();
		Bundle args = new Bundle();
        args.putInt(Constants.KEY_ID, id);
		fragment.setArguments(args);
		return fragment;
	}

	@InjectPresenter
	DialogTFPresenter tfPresenter;

	@ProvidePresenter
	DialogTFPresenter providePresenter(){
		DialogTFPresenter tfPresenter = new DialogTFPresenter(settableByFunction);
		App.getAppComponent().inject(tfPresenter);
        if (getArguments() != null) {
            tfPresenter.getTF(getArguments().getInt(Constants.KEY_ID));
        }
        return tfPresenter;
    }

	@Override
	protected View createView(){
		View view = super.createView();
		adapter = new FunctionDialogAdapter(tfPresenter);
		recyclerView.setAdapter(adapter);
		return view;
	}

	@Override
	public String getTitle(Context context) {
		return context.getString(R.string.TF);
	}
}
