package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.data.repositories.StorableOTF;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByFunction;

@InjectViewState
public class DialogOTFPresenter extends DialogFunctionPresenter {

	@Inject StorableOTF storableOTF;

	public DialogOTFPresenter(SettableByFunction settableByFunction) {
		super(settableByFunction);
	}

	public void getOTF() {
		getViewState().showProgressBar();
		disposable = storableOTF.getOTFList()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(otfList -> {
				list.addAll(otfList);
				ifRequestSuccess();
			}, e -> {
				getViewState().hideProgressBar();
				Log.e("getOTF(): e", e.getMessage());
			});

	}

	@Override
	public void selectItem(int position) {
		settableByFunction.setFunction(list.get(position), false);
	}
}
