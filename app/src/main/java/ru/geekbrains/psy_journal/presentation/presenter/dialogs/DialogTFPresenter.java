package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.data.repositories.StorableTF;
import ru.geekbrains.psy_journal.data.repositories.model.Functional;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByFunction;

@InjectViewState
public class DialogTFPresenter extends DialogFunctionPresenter {

	@Inject StorableTF storableTF;

	public DialogTFPresenter(SettableByFunction settableByFunction) {
		super(settableByFunction);
	}

	public void getTF(int idOTF) {
		getViewState().showProgressBar();
		disposable = storableTF.getTFList(idOTF)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(tfs -> {
				list.addAll(tfs);
				ifRequestSuccess();
			}, e -> {
				getViewState().hideProgressBar();
				Log.e("getTF(): e", e.getMessage());
			});
	}

	@Override
	public void selectItem(int position) {
		Functional function = list.get(position);
		settableByFunction.setFunction(function, isOtherActivity(function));
	}

	private boolean isOtherActivity(Functional functional) {
		return functional.getCode().endsWith(Constants.CODE_OF_OTHER_ACTIVITY_SUFFIX);
	}
}
