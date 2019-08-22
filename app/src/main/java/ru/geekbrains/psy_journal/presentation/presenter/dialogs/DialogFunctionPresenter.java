package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.data.repositories.model.Functional;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.FunctionView;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.Displayed;

@InjectViewState
public class DialogFunctionPresenter extends MvpPresenter<FunctionView> implements Bindable {

	@Inject	RoomHelper roomHelper;

	private final List<Functional> list = new ArrayList<>();
	private Disposable disposable;

	public void getOTF() {
		getViewState().showProgressBar();
		disposable = roomHelper.getOTFList()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(otfList -> {
					list.addAll(otfList);
					ifRequestSuccess();
				}, e -> {
					getViewState().hideProgressBar();
					Log.e("getOTF(): ", e.getMessage());
			});

	}

	public void getTF(int idOTF) {
		getViewState().showProgressBar();
		disposable = roomHelper.getTFList(idOTF)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(tfs -> {
					list.addAll(tfs);
					ifRequestSuccess();
				}, e -> {
					getViewState().hideProgressBar();
					Log.e("getTF(): ", e.getMessage());
			});
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
					Log.e("getTD: ", e.getMessage());
			});
	}

	private void ifRequestSuccess() {
		getViewState().updateRecyclerView();
		getViewState().hideProgressBar();
	}

	@Override
	public void bindView(Displayed displayed, int position) {
		Functional function = list.get(position);
        displayed.bind(String.format("%s. %s", function.getCode(), function.getName()));
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	@Override
	public void selectItem(int position) {
		getViewState().openNewFeature(list.get(position));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (disposable != null) disposable.dispose();
	}
}
