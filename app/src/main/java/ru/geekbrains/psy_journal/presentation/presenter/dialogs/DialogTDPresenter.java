package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.data.repositories.StorableTD;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByFunction;

@InjectViewState
public class DialogTDPresenter extends DialogFunctionPresenter {

	@Inject	StorableTD storableTD;

	public DialogTDPresenter(SettableByFunction settableByFunction) {
		super(settableByFunction);
	}

	public void getTD(int idTF) {
		getViewState().showProgressBar();
		disposable = storableTD.getTDList(idTF)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(tds -> {
				list.addAll(tds);
				ifRequestSuccess();
			}, e -> {
				getViewState().hideProgressBar();
				Log.e("getTD: e", e.getMessage());
			});
	}


	@Override
	public void selectItem(int position) {
		settableByFunction.setFunction(list.get(position), true);
	}
}
