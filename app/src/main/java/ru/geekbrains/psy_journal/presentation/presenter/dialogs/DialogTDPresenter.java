package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByFunction;

@InjectViewState
public class DialogTDPresenter extends DialogFunctionPresenter {


	public DialogTDPresenter(SettableByFunction settableByFunction) {
		super(settableByFunction);
	}

	public void getTD(int idTF) {
		getViewState().showProgressBar();
		disposable = roomHelper.getTDList(idTF)
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
